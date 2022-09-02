package com.microservices.deposit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long depositId;

    private BigDecimal amount;

    private Long billId;

    private OffsetDateTime creationDate;

    private String email;

    public Deposit(BigDecimal amount, Long billId, OffsetDateTime creationDate, String email) {
        this.amount = amount;
        this.billId = billId;
        this.creationDate = creationDate;
        this.email = email;
    }

    public Deposit() {
    }

    public Long getDepositId() {
        return depositId;
    }

    public void setDepositId(Long depositId) {
        this.depositId = depositId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "depositId=" + depositId +
                ", amount=" + amount +
                ", billId=" + billId +
                ", creationDate=" + creationDate +
                ", email='" + email + '\'' +
                '}';
    }
}
