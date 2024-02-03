package com.Bank.Service;

import com.Bank.Dto.*;

public interface UserService {

	BankResponse createAccount(UserRequest userRequest);

	BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
	
	String nameEnquiry(EnquiryRequest enquiryRequest);
	
	BankResponse creditAccount(DebitCreditRequest creditRequest);
	
	BankResponse debitAccount(DebitCreditRequest debitRequest);
	BankResponse transfer(TransferRequest transferRequest);
}
