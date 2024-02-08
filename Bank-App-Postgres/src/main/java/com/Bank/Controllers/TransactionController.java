package com.Bank.Controllers;

import com.Bank.Models.Transaction;
import com.Bank.Service.Impl.BankStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankStatement")
public class TransactionController {

    @Autowired
    private BankStatement bankStatement;
    @GetMapping()
    public List<Transaction> generateStatement(@RequestParam String accountNumber,String startDate,String endDate)
    {
        return bankStatement.generateStatement(accountNumber,startDate,endDate);
    }
}
