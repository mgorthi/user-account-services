package com.anz.services.useraccounts.model;

public final class SavingsAccount extends AbstractAccount {
	
	public SavingsAccount(String accountNumber, String accountName) {
		super(accountNumber, accountName);
	}
	
	@Override
	public AccountType getAccountType() {
		return AccountType.SAVINGS;
	}

}
