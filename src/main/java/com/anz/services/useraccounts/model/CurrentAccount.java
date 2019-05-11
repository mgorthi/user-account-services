package com.anz.services.useraccounts.model;

public final class CurrentAccount extends AbstractAccount {
	
	public CurrentAccount(String accountNumber, String accountName, AccountBalance accountBalance) {
		super(accountNumber, accountName, accountBalance);
	}
	
	@Override
	public AccountType getAccountType() {
		return AccountType.CURRENT;
	}

}
