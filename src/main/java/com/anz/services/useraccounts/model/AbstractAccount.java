package com.anz.services.useraccounts.model;

import java.util.Currency;
import java.util.Objects;

public abstract class AbstractAccount implements Account {
	
	private final String accountNumber;
	private final String accountName;
	private final Currency currency;
	
	protected AbstractAccount(final String accountNumber, final String accountName, final Currency currency) {
		Objects.requireNonNull(accountNumber, "accountNumber cannot be null");
		Objects.requireNonNull(accountName, "accountName cannot be null");
		Objects.requireNonNull(currency, "currency cannot be null");
		this.accountNumber = accountNumber;
		this.accountName = accountName;
		this.currency = currency;
	}
	
	@Override
	public String getAccountName() {
		return accountName;
	}
	
	@Override
	public String getAccountNumber() {
		return accountNumber;
	}

	@Override
	public Currency getCurrency() {
		return currency;
	}
	
}
