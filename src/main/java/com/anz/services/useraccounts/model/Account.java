package com.anz.services.useraccounts.model;

import java.util.Currency;


public interface Account {
	
	static enum AccountType {
		SAVINGS, CURRENT
	}

	Long getId();
	
	String getAccountNumber();
	
	String getAccountName();

	AccountType getAccountType();
	
	Currency getCurrency();
	
}
