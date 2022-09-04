package com.microservices.deposit.rest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class BillResponseDto {

    private Long billId;

    private Long accountId;

    private BigDecimal amount;

    private boolean isDefault;

    private OffsetDateTime creationDate;

    private boolean overdraftEnabled;

    public BillResponseDto(Long billId, Long accountId, BigDecimal amount, boolean isDefault, OffsetDateTime creationDate, boolean overdraftEnabled) {
        this.billId = billId;
        this.accountId = accountId;
        this.amount = amount;
        this.isDefault = isDefault;
        this.creationDate = creationDate;
        this.overdraftEnabled = overdraftEnabled;
    }

    public BillResponseDto() {
    }

    public Long getBillId() {
        return billId;
    }

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

    public void setBillId(Long billId) {
        this.billId = billId;
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
