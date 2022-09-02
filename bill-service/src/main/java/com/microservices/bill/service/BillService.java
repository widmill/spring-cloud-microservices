package com.microservices.bill.service;

import com.microservices.bill.exception.BillNotFoundException;
import com.microservices.bill.model.Bill;
import com.microservices.bill.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;


@Service
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill getBillById(Long id) {

        return billRepository.findById(id).orElseThrow(
                () -> new BillNotFoundException("Can't find bill with id = " + id));
    }

    public Long createBill(Long accountId, BigDecimal amount, boolean isDefault, boolean overdraftEnabled) {

        Bill account = new Bill(accountId, amount, isDefault, OffsetDateTime.now(), overdraftEnabled);

        return billRepository.save(account).getAccountId();
    }

    public Bill updateBill(Long billId, Long accountId, BigDecimal amount,
                           boolean isDefault, boolean overdraftEnabled) {
        Bill bill = new Bill(accountId, amount, isDefault, overdraftEnabled);
        bill.setBillId(billId);

        return billRepository.save(bill);
    }

    public Bill deleteBill(Long id) {

        Bill deletedBill = getBillById(id);

        billRepository.deleteById(id);
        return deletedBill;
    }

    public List<Bill> getBillsByAccountId(Long accountId) {
        return billRepository.getBillsByAccountId(accountId);
    }
}
