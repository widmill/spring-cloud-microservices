package com.microservices.deposit.exception;

public class DepositServiceException extends RuntimeException{
    public DepositServiceException(String message) {
        super(message);
    }
}
