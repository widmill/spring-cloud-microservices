package com.microservices.bill.controller.dto;

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
}
