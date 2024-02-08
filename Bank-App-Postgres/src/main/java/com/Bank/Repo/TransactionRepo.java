package com.Bank.Repo;

import com.Bank.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction,String> {
}
