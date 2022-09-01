package com.microservices.account.controller.dto;

import java.time.OffsetDateTime;
import java.util.List;

public class AccountRequestDto {

    private String name;

    private String email;

    private String phone;

    private OffsetDateTime creationDate;

    private List<Long> bills;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public List<Long> getBills() {
        return bills;
    }
}
