package com.Bank.Service;

import com.Bank.Dto.BankResponse;
import com.Bank.Dto.DebitCreditRequest;
import com.Bank.Dto.EnquiryRequest;
import com.Bank.Dto.UserRequest;

public interface UserService {

	BankResponse createAccount(UserRequest userRequest);

	BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
	
	String nameEnquiry(EnquiryRequest enquiryRequest);
	
	BankResponse creditAccount(DebitCreditRequest creditRequest);
	
	BankResponse debitAccount(DebitCreditRequest debitRequest);
}
