package com.anz.services.useraccounts.model;

public abstract class AbstractAccount implements Account {
	
	private final String accountNumber;
	private final String accountName;
	
	protected AbstractAccount(String accountNumber, String accountName) {
		this.accountNumber = accountNumber;
		this.accountName = accountName;
	}
	
	@Override
	public String getAccountName() {
		return accountName;
	}
	
	@Override
	public String getAccountNumber() {
		return accountNumber;
	}
	
}
