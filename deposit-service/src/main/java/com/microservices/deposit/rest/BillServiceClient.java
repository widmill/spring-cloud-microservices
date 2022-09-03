package com.microservices.deposit.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "bill-service", decode404 = true)
public interface BillServiceClient {

    @RequestMapping(value = "/bills/{billId}", method = RequestMethod.GET)
    BillResponseDto getBillById(@PathVariable("billId") Long billId);

    @RequestMapping(value = "/bills/{billId}", method = RequestMethod.PUT)
    void update(@PathVariable("billId") Long billId, BillRequestDto billRequest);

    @RequestMapping(value = "bills/accounts/{accountId}", method = RequestMethod.GET)
    List<BillResponseDto> getBillByAccountId(@PathVariable Long accountId);
}
