package com.Bank.Service;

import com.Bank.Dto.TransactionDto;
import com.Bank.Models.Transaction;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
}
