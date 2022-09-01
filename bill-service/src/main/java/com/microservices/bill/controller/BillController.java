package com.microservices.bill.controller;

import com.microservices.bill.controller.dto.BillRequestDto;
import com.microservices.bill.controller.dto.BillResponseDto;
import com.microservices.bill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/{billId}")
    public BillResponseDto getBill(@PathVariable Long billId) {
        return new BillResponseDto(billService.getBillById(billId));
    }

    @PostMapping()
    public Long createBill(@RequestBody BillRequestDto billRequest) {
        return billService.createBill(billRequest.getAccountId(), billRequest.getAmount(),
                billRequest.isDefault(), billRequest.isOverdraftEnabled());
    }

    @PutMapping("/{billId}")
    public BillResponseDto updateAccount(@PathVariable Long billId,
                                            @RequestBody BillRequestDto billRequest) {

        return new BillResponseDto(billService.updateBill(billId,billRequest.getAccountId(), billRequest.getAmount(),
                billRequest.isDefault(), billRequest.isOverdraftEnabled()));
    }

    @DeleteMapping("/{billId}")
    public BillResponseDto deleteAccount(@PathVariable Long billId) {
        return new BillResponseDto(billService.deleteBill(billId));
    }
}
