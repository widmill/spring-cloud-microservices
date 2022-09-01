package com.microservices.account.service;

import com.microservices.account.exception.AccountNotFoundException;
import com.microservices.account.model.Account;
import com.microservices.account.repository.AccountRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountById(Long accountId, Logger log) {
        log.info("Looking for account with id = {}", accountId);

        return accountRepository.findById(accountId).orElseThrow(
                () -> new AccountNotFoundException("Can't find account with id = " + accountId));
    }

    public Long createAccount(String name, String email, String phone, List<Long> bills, Logger log) {
        log.info("Creating account: [name = {}, email = {}, phone = {}, bills = {}]", name, email, phone, bills);

        Account account = new Account(name, email, phone, OffsetDateTime.now(), bills);

        return accountRepository.save(account).getAccountId();
    }

    public Account updateAccount(Long accountId, String name, String email,
                                 String phone, List<Long> bills, Logger log) {

        log.info("Updating account: [accountId = {}, name = {}, email = {}, phone = {}, bills = {}]", accountId, name, email, phone, bills);

        Account account = new Account(name, email, phone, OffsetDateTime.now(), bills);
        account.setAccountId(accountId);

        return accountRepository.save(account);
    }

    public Account deleteAccount(Long id, Logger log) {
        log.info("Updating account: [accountId = {}]", id);

        Account deletedAccount = getAccountById(id, log);

        accountRepository.deleteById(id);
        return deletedAccount;
    }
}
