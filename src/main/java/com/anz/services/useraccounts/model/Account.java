package com.anz.services.useraccounts.model;

public interface Account {
	
	static enum AccountType {
		SAVINGS, CURRENT
	}

	String getAccountNumber();
	
	String getAccountName();

	AccountType getAccountType();
	
	AccountBalance getAccountBalance();

}
