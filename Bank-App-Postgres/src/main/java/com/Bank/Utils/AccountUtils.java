package com.Bank.Utils;

import java.time.Year;

public class AccountUtils {
	
	public static final String ACCOUNT_EXISTED_CODE="001";
	public static final String ACCOUNT_EXISTED_MESSAGE="This user already has an account created!";

	public static final String ACCOUNT_CREATION_SUCCESS_CODE="002";
	public static final String ACCOUNT_CREATION_MESSAGE="Account has been successfully created";

	public static final String ACCOUNT_NOT_EXISTED_CODE="003";
	public static final String ACCOUNT_NOT_EXISTED_MESSAGE="No Account Found";

	public static final String ACCOUNT_FOUND_CODE="004";
	public static final String ACCOUNT_FOUND_MESSAGE="User account Found";

	public static final String ACCOUNT_CREDITED_SUCCESS_CODE="005";
	public static final String ACCOUNT_CREDITED_MESSAGE="Account has been credited successfully";

	public static final String INSUFFICIENT_BALANCE_CODE="006";
	public static final String INSUFFICIENT_BALANCE_MESSAGE="INSUFFICIENT BALANCE!!";
	
	public static final String ACCOUNT_DEBITED_SUCCESS_CODE="007";
	public static final String ACCOUNT_DEBITED_MESSAGE="Account has been debited successfully";

	public static final String SOURCE_ACCOUNT_NOT_EXISTED_CODE="008";
	public static final String SOURCE_ACCOUNT_NOT_EXISTED_MESSAGE="No Source Account Found";

	public static final String DESTINATION_ACCOUNT_NOT_EXISTED_CODE="009";
	public static final String DESTINATION_ACCOUNT_NOT_EXISTED_MESSAGE="No Destination Account Found";

	public static final String ACCOUNT_TRANSFER_CODE="010";
	public static final String ACCOUNT_TRANSFER_MESSAGE="Transfer Successful";

	public static String generateAccountNumber()
	{
		Year currentYear=Year.now();
		int min=100000;
		int max=999999;
		
		int randomNumber=(int)Math.floor(Math.random()*(max-min +1));
		
		String year=String.valueOf(currentYear);
		String number=String.valueOf(randomNumber);
		StringBuilder accountNumber=new StringBuilder();
		return accountNumber.append(year).append(number).toString();
	}
}
