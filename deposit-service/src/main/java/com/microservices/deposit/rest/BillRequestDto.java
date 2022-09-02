package com.microservices.deposit.rest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class BillRequestDto {

    private Long accountId;

    private BigDecimal amount;

    private boolean isDefault;

    private OffsetDateTime creationDate;

    private boolean overdraftEnabled;

    public Long getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public boolean isOverdraftEnabled() {
        return overdraftEnabled;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setOverdraftEnabled(boolean overdraftEnabled) {
        this.overdraftEnabled = overdraftEnabled;
    }
}
