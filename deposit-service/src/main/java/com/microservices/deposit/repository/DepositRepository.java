package com.microservices.deposit.repository;

import com.microservices.deposit.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

    List<Deposit> findDepositsByEmail(String email);
}
