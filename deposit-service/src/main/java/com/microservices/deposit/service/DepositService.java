package com.microservices.deposit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.deposit.controller.dto.DepositResponseDto;
import com.microservices.deposit.exception.DepositServiceException;
import com.microservices.deposit.model.Deposit;
import com.microservices.deposit.repository.DepositRepository;
import com.microservices.deposit.rest.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class DepositService {

    public static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    public static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";


    private final DepositRepository depositRepository;

    private final AccountServiceClient accountServiceClient;

    private final BillServiceClient billServiceClient;

    private final RabbitTemplate rabbitTemplate;


    public DepositService(DepositRepository depositRepository, AccountServiceClient accountServiceClient, BillServiceClient billServiceClient, RabbitTemplate rabbitTemplate) {
        this.depositRepository = depositRepository;
        this.accountServiceClient = accountServiceClient;
        this.billServiceClient = billServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public DepositResponseDto deposit(Long accountId, Long billId, BigDecimal amount) {
        if (accountId == null && billId == null) {
            throw new DepositServiceException("Account id or bill id must not be null");
        }

        if (billId != null) {
            BillResponseDto billResponse = billServiceClient.getBillById(billId);
            BillRequestDto billRequest = createBillRequest(amount, billResponse);

            billServiceClient.update(billId, billRequest);

            AccountResponseDto accountResponse = accountServiceClient.getAccountById(billResponse.getAccountId());

            depositRepository.save(new Deposit(amount, billId, OffsetDateTime.now(), accountResponse.getEmail()));

            return createResponse(amount, accountResponse);
        }
        BillResponseDto defaultBill = getDefaultBill(accountId);
        BillRequestDto billRequest = createBillRequest(amount, defaultBill);

        billServiceClient.update(defaultBill.getBillId(), billRequest);

        AccountResponseDto account = accountServiceClient.getAccountById(accountId);
        depositRepository.save(new Deposit(amount, defaultBill.getBillId(), OffsetDateTime.now(), account.getEmail()));

        return createResponse(amount, account);
    }

    private BillResponseDto getDefaultBill(Long accountId) {
        return billServiceClient.getBillByAccountId(accountId)
                .stream()
                .filter(BillResponseDto::isDefault)
                .findAny()
                .orElseThrow(() -> new DepositServiceException("Unable to find default bill with account id: " + accountId));
    }

    private BillRequestDto createBillRequest(BigDecimal amount, BillResponseDto billResponse) {
        BillRequestDto billRequest = new BillRequestDto();

        billRequest.setAccountId(billResponse.getAccountId());
        billRequest.setCreationDate(billResponse.getCreationDate());
        billRequest.setDefault(billResponse.isDefault());
        billRequest.setOverdraftEnabled(billResponse.isOverdraftEnabled());
        billRequest.setAmount(billResponse.getAmount().add(amount));

        return billRequest;
    }

    private DepositResponseDto createResponse(BigDecimal amount, AccountResponseDto accountResponse) {
        DepositResponseDto depositResponse = new DepositResponseDto(amount, accountResponse.getEmail());

        ObjectMapper mapper = new ObjectMapper();

        try {
            rabbitTemplate.convertAndSend(
                    TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT, mapper.writeValueAsString(depositResponse));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DepositServiceException("Can't send message");
        }
        return depositResponse;
    }
}
