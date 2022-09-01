package com.microservices.account.controller;

import com.microservices.account.controller.dto.AccountRequestDto;
import com.microservices.account.controller.dto.AccountResponseDto;
import com.microservices.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public AccountResponseDto getAccount(@PathVariable Long accountId) {
        return new AccountResponseDto(accountService.getAccountById(accountId));
    }

    @PostMapping()
    public Long createAccount(@RequestBody AccountRequestDto accountRequest) {
        return accountService.createAccount(accountRequest.getName(), accountRequest.getEmail(),
                accountRequest.getEmail(), accountRequest.getBills());
    }

    @PutMapping("/{accountId}")
    public AccountResponseDto updateAccount(@PathVariable Long accountId,
            @RequestBody AccountRequestDto accountRequest) {

        return new AccountResponseDto(accountService.updateAccount(accountId, accountRequest.getName(),
                accountRequest.getEmail(), accountRequest.getPhone(), accountRequest.getBills()));
    }

    @DeleteMapping("/{accountId}")
    public AccountResponseDto deleteAccount(@PathVariable Long accountId) {
        return new AccountResponseDto(accountService.deleteAccount(accountId));
    }
}
