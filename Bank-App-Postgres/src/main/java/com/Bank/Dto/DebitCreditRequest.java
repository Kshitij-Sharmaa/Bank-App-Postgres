package com.Bank.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitCreditRequest {

	private String accountNumber;
	private BigDecimal amount;
}
