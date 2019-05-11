package com.anz.services.useraccounts.model;

public abstract class AbstractAccount implements Account {
	
	private final String accountNumber;
	private final String accountName;
	private final AccountBalance accountBalance;
	
	protected AbstractAccount(String accountNumber, String accountName, AccountBalance accountBalance) {
		this.accountNumber = accountNumber;
		this.accountName = accountName;
		this.accountBalance = accountBalance;
	}
	
	@Override
	public AccountBalance getAccountBalance() {
		return accountBalance;
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
