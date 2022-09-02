package com.microservices.notification.service;

import java.math.BigDecimal;

public class DepositResponseDto {
    private BigDecimal amount;

    private String email;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
