package com.microservices.deposit.repository;

import com.microservices.deposit.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Long> {


}
