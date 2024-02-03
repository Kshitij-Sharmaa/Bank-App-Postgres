package com.Bank.Service.Impl;

import com.Bank.Dto.*;
import com.Bank.Models.User;
import com.Bank.Repo.UserRepo;
import com.Bank.Service.UserService;
import com.Bank.Utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService{

	@Autowired UserRepo userRepo;

	@Override
	public BankResponse createAccount(UserRequest userRequest) {
		if(userRepo.existsByEmail(userRequest.getEmail()))
		{
			return BankResponse.builder()
					.responceCode(AccountUtils.ACCOUNT_EXISTED_CODE)
					.responseMessage(AccountUtils.ACCOUNT_EXISTED_MESSAGE)
					.accountInfo(null)
					.build();
		}
		
		User newUser=User.builder().
				firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.otherName(userRequest.getOtherName())
				.gender(userRequest.getGender())
				.address(userRequest.getAddress())
				.stateOfOrigin(userRequest.getStateOfOrigin())
				.accountNumber(AccountUtils.generateAccountNumber())
				.accountBalance(BigDecimal.ZERO)
				.email(userRequest.getEmail())
				.phoneNumber(userRequest.getPhoneNumber())
				.alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
				.status("ACTIVE")
				.build();
		
		User savedUser=userRepo.save(newUser);
		return BankResponse.builder()
				.responceCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_CODE)
				.responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
				.accountInfo(
						AccountInfo.builder()
						.accountBalance(savedUser.getAccountBalance())
						.accountNumber(savedUser.getAccountNumber())
						.accountName(savedUser.getFirstName()+" "+ savedUser.getLastName()+" "+savedUser.getOtherName())
						.build()
						)
				.build();
	}

	@Override
	public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
	Boolean isAccountExist=userRepo.existsByAccountNumber(enquiryRequest.getAccountNumber());
		if (!isAccountExist) {
			return BankResponse.builder()
					.responceCode(AccountUtils.ACCOUNT_NOT_EXISTED_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXISTED_MESSAGE)
					.accountInfo(null)
					.build();
		}
		User foundUser=userRepo.findByAccountNumber(enquiryRequest.getAccountNumber());
		return BankResponse.builder()
				.responceCode(AccountUtils.ACCOUNT_FOUND_CODE)
				.responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
				.accountInfo(AccountInfo.builder()
						.accountName(foundUser.getFirstName()+ " "+foundUser.getLastName())
						.accountNumber(foundUser.getAccountNumber())
						.accountBalance(foundUser.getAccountBalance())
						.build())
				.build();
	}

	@Override
	public String nameEnquiry(EnquiryRequest enquiryRequest) {
		Boolean isAccountExist=userRepo.existsByAccountNumber(enquiryRequest.getAccountNumber());
		if (!isAccountExist) {
			return AccountUtils.ACCOUNT_NOT_EXISTED_MESSAGE;
		}
		User foundUser=userRepo.findByAccountNumber(enquiryRequest.getAccountNumber());
		return foundUser.getFirstName()+ " "+foundUser.getLastName();
	}

	@Override
	public BankResponse creditAccount(DebitCreditRequest creditRequest) {
		Boolean isAccountExist=userRepo.existsByAccountNumber(creditRequest.getAccountNumber());
		if (!isAccountExist) {
			return BankResponse.builder()
					.responceCode(AccountUtils.ACCOUNT_NOT_EXISTED_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXISTED_MESSAGE)
					.accountInfo(null)
					.build();
		}
		User userToCredit=userRepo.findByAccountNumber(creditRequest.getAccountNumber());
		userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditRequest.getAmount()));
		userRepo.save(userToCredit);
		return BankResponse.builder()
				.responceCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS_CODE)
				.responseMessage(AccountUtils.ACCOUNT_CREDITED_MESSAGE)
				.accountInfo(AccountInfo.builder()
						.accountName(userToCredit.getFirstName()+" "+userToCredit.getLastName())
						.accountBalance(userToCredit.getAccountBalance())
						.accountNumber(userToCredit.getAccountNumber())
						.build())
				.build();
	}

	@Override
	public BankResponse debitAccount(DebitCreditRequest debitRequest) {
		Boolean isAccountExist=userRepo.existsByAccountNumber(debitRequest.getAccountNumber());
		if (!isAccountExist) {
			return BankResponse.builder()
					.responceCode(AccountUtils.ACCOUNT_NOT_EXISTED_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXISTED_MESSAGE)
					.accountInfo(null)
					.build();
		}
		User userToDebit=userRepo.findByAccountNumber(debitRequest.getAccountNumber());
		
		int amount=debitRequest.getAmount().intValue();
		int userBalance=userToDebit.getAccountBalance().intValue();
		
		if (amount>userBalance) {
			return BankResponse.builder()
			.responceCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
			.responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
			.accountInfo(null)
			.build();
		}
		else {
			userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(debitRequest.getAmount()));
			userRepo.save(userToDebit);
			return BankResponse.builder()
					.responceCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
					.accountInfo(AccountInfo.builder()
							.accountName(userToDebit.getFirstName()+" "+userToDebit.getLastName())
							.accountBalance(userToDebit.getAccountBalance())
							.accountNumber(userToDebit.getAccountNumber())
							.build())
					.build();
		}
	}

	@Override
	public BankResponse transfer(TransferRequest transferRequest) {
		boolean isSourceAccountExist=userRepo.existsByAccountNumber(transferRequest.getSourceAccount());
		boolean isDestinationAccountExist=userRepo.existsByAccountNumber(transferRequest.getDestinationAccount());
		if (!isSourceAccountExist)
		{
			return BankResponse.builder()
					.responceCode(AccountUtils.SOURCE_ACCOUNT_NOT_EXISTED_CODE)
					.responseMessage(AccountUtils.SOURCE_ACCOUNT_NOT_EXISTED_MESSAGE)
					.accountInfo(null)
					.build();
		}

		if (!isDestinationAccountExist)
		{
			return BankResponse.builder()
					.responceCode(AccountUtils.DESTINATION_ACCOUNT_NOT_EXISTED_CODE)
					.responseMessage(AccountUtils.DESTINATION_ACCOUNT_NOT_EXISTED_MESSAGE)
					.accountInfo(null)
					.build();
		}
		User sourceAccountUser=userRepo.findByAccountNumber(transferRequest.getSourceAccount());

		if (transferRequest.getAmount().compareTo(sourceAccountUser.getAccountBalance())<0)
		{
			return BankResponse.builder()
					.responceCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
					.responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
					.accountInfo(null)
					.build();
		}
		sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(transferRequest.getAmount()));
		userRepo.save(sourceAccountUser);

		User destinationAccountUser=userRepo.findByAccountNumber(transferRequest.getDestinationAccount());
		destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(transferRequest.getAmount()));
		userRepo.save(destinationAccountUser);

		return BankResponse.builder()
				.responceCode(AccountUtils.ACCOUNT_TRANSFER_CODE)
				.responseMessage(AccountUtils.ACCOUNT_TRANSFER_MESSAGE)
				.accountInfo(null)
				.build();
	}


}
