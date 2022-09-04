package com.microservices.depositservice.service;

import com.microservices.deposit.controller.dto.DepositResponseDto;
import com.microservices.deposit.exception.DepositServiceException;
import com.microservices.deposit.repository.DepositRepository;
import com.microservices.deposit.rest.AccountResponseDto;
import com.microservices.deposit.rest.AccountServiceClient;
import com.microservices.deposit.rest.BillResponseDto;
import com.microservices.deposit.rest.BillServiceClient;
import com.microservices.deposit.service.DepositService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class DepositServiceTest {

    @Mock
    private DepositRepository depositRepository;

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private BillServiceClient billServiceClient;

    @Mock
    private RabbitTemplate rabbitTemplate;


    @InjectMocks
    private DepositService depositService;


    @Test
    public void depositServiceTest_withBillId() {
        BillResponseDto billResponseDto = createBillResponseDto();
        Mockito.when(billServiceClient.getBillById(ArgumentMatchers.anyLong())).thenReturn(billResponseDto);
        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong())).thenReturn(createAccountResponseDto());
        DepositResponseDto deposit = depositService.deposit(null, 1L, BigDecimal.valueOf(1000));

        Assertions.assertThat(deposit.getEmail()).isEqualTo("kakoi-toemail@gmail.com");

    }


    @Test(expected = DepositServiceException.class)
    public void depositServiceTest_exception() {
        depositService.deposit(null, null, BigDecimal.valueOf(1000));
    }

    private AccountResponseDto createAccountResponseDto() {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setAccountId(1L);
        accountResponseDto.setBills(Arrays.asList(1L, 2L, 3L));
        accountResponseDto.setCreationDate(OffsetDateTime.now());
        accountResponseDto.setEmail("kakoi-toemail@gmail.com");
        accountResponseDto.setName("Name");
        accountResponseDto.setPhone("+123456");
        return accountResponseDto;
    }

    private BillResponseDto createBillResponseDto() {
        BillResponseDto billResponseDto = new BillResponseDto();
        billResponseDto.setAccountId(1L);
        billResponseDto.setAmount(BigDecimal.valueOf(1000));
        billResponseDto.setBillId(1L);
        billResponseDto.setCreationDate(OffsetDateTime.now());
        billResponseDto.setDefault(true);
        billResponseDto.setOverdraftEnabled(true);
        return billResponseDto;

    }
}
