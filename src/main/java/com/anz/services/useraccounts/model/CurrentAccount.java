package com.anz.services.useraccounts.model;

public final class CurrentAccount extends AbstractAccount {
	
	public CurrentAccount(String accountNumber, String accountName) {
		super(accountNumber, accountName);
	}
	
	@Override
	public AccountType getAccountType() {
		return AccountType.CURRENT;
	}

}
