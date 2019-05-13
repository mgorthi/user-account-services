package com.anz.services.useraccounts.model;

import java.util.Currency;

import javax.persistence.Entity;

@Entity
public final class SavingsAccount extends AbstractAccount {
	
	private AccountType accountType = AccountType.SAVINGS;
	
	public SavingsAccount() {
		super();
	}
	
	public SavingsAccount(final String accountNumber, final String accountName, final Currency currency) {
		super(accountNumber, accountName, currency);
	}
	
	@Override
	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
}
