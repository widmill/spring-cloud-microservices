package com.microservices.bill.repository;

import com.microservices.bill.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> getBillsByAccountId(Long accountId);
}
