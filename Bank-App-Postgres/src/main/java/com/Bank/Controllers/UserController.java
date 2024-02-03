package com.Bank.Controllers;

import com.Bank.Dto.*;
import com.Bank.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired UserService userservice;

	@PostMapping()
	public ResponseEntity<BankResponse> createAccount(@RequestBody UserRequest userRequest) {
		BankResponse response= userservice.createAccount(userRequest);
		 return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("balanceEnquiry")
	public ResponseEntity<BankResponse> balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
		BankResponse response=userservice.balanceEnquiry(enquiryRequest);
		return ResponseEntity.status(HttpStatus.FOUND).body(response);
	}
	
	@GetMapping("nameEnquiry")
	public ResponseEntity<String> nameEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
		String response=userservice.nameEnquiry(enquiryRequest);
		return ResponseEntity.status(HttpStatus.FOUND).body(response);
	}
	
	@PostMapping("credit")
	public BankResponse creditAccount(@RequestBody DebitCreditRequest creditRequest) {
		return userservice.creditAccount(creditRequest);
	}
	
	@PostMapping("debit")
	public BankResponse debitAccount(@RequestBody DebitCreditRequest debitRequest) {
		return userservice.debitAccount(debitRequest);
	}

	@PostMapping("transfer")
	public BankResponse transfer(@RequestBody TransferRequest transferRequest)
	{
		return userservice.transfer(transferRequest);
	}
}
