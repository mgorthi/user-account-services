package com.anz.services.useraccounts.model;

public final class SavingsAccount extends AbstractAccount {
	
	public SavingsAccount(String accountNumber, String accountName, AccountBalance accountBalance) {
		super(accountNumber, accountName, accountBalance);
	}
	
	@Override
	public AccountType getAccountType() {
		return AccountType.SAVINGS;
	}

}
