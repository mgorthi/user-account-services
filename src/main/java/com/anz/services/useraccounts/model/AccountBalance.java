package com.anz.services.useraccounts.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;

public final class AccountBalance {
	
	private final BigDecimal openingBalance;
	private final Currency currency;
	private final LocalDate balanceDate;
	
	public AccountBalance(Double openingBalance, Currency currency, LocalDate balanceDate) {
		Objects.requireNonNull(openingBalance, "balance cannot be null");
		Objects.requireNonNull(currency, "currency cannot be null");
		Objects.requireNonNull(balanceDate, "balanceDate cannot be null");
		this.openingBalance = new BigDecimal(openingBalance);
		this.currency = currency;
		this.balanceDate = balanceDate;
	}

	public String getOpeningBalance() {
		return openingBalance.toString();
	}

	public Currency getCurrency() {
		return currency;
	}

	public LocalDate getBalanceDate() {
		return balanceDate;
	}
	
}
