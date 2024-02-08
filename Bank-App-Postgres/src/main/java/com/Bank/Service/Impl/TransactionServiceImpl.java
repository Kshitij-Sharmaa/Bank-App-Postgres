package com.Bank.Service.Impl;

import com.Bank.Dto.TransactionDto;
import com.Bank.Models.Transaction;
import com.Bank.Repo.TransactionRepo;
import com.Bank.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepo transactionRepo;
    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction=Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status("ACTIVE")
                .build();
        transactionRepo.save(transaction);
    }
}
