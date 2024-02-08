package com.Bank.Service.Impl;

import com.Bank.Models.Transaction;
import com.Bank.Repo.TransactionRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
public class BankStatement {
    @Autowired
    TransactionRepo transactionRepo;
    public List<Transaction> generateStatement(String accountNumber,String startDate,String endDate){
        LocalDate start=LocalDate.parse(startDate,DateTimeFormatter.ISO_DATE);
        LocalDate end=LocalDate.parse(endDate,DateTimeFormatter.ISO_DATE);

        return transactionRepo.findAll().stream().filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedAt().isEqual(start)).filter(transaction -> transaction.getCreatedAt().isEqual(end)).toList();
    }
}
