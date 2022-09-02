package com.microservices.deposit.controller;


import com.microservices.deposit.controller.dto.DepositRequestDto;
import com.microservices.deposit.controller.dto.DepositResponseDto;
import com.microservices.deposit.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositController {

    private final DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/deposits")
    public DepositResponseDto deposit(@RequestBody DepositRequestDto depositRequest) {
        return depositService.deposit(
                depositRequest.getAccountId(), depositRequest.getBillId(), depositRequest.getAmount());
    }
}
