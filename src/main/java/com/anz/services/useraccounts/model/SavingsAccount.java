package com.anz.services.useraccounts.model;

import java.util.Currency;

public final class SavingsAccount extends AbstractAccount {
	
	public SavingsAccount(final String accountNumber, final String accountName, final Currency currency) {
		super(accountNumber, accountName, currency);
	}
	
	@Override
	public AccountType getAccountType() {
		return AccountType.SAVINGS;
	}

}
