package com.anz.services.useraccounts.model;

import java.util.Currency;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public final class SavingsAccount extends Account {
	
	@NotNull
	private AccountType accountType = AccountType.SAVINGS;
	
	public SavingsAccount() {
		super();
	}
	
	public SavingsAccount(final String accountNumber, final String accountName, final Currency currency, final AccountHolder accountHolder) {
		super(accountNumber, accountName, currency, accountHolder);
	}
	
	@Override
	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
}
