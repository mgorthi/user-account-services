package com.anz.services.useraccounts.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.util.StringUtils;

public final class AccountBalance {
	
	private final BigDecimal openingBalance;
	private final LocalDate balanceDate;
	
	public AccountBalance(final String openingBalance, final LocalDate balanceDate) {
		Objects.requireNonNull(openingBalance, "balance cannot be null");
		Objects.requireNonNull(balanceDate, "balanceDate cannot be null");
		if (StringUtils.isEmpty(openingBalance)) {
			throw new IllegalArgumentException("Account balance cannot be empty");
		}
		this.openingBalance = new BigDecimal(openingBalance);
		this.balanceDate = balanceDate;
	}

	public String getOpeningBalance() {
		return openingBalance.toString();
	}

	public LocalDate getBalanceDate() {
		return balanceDate;
	}
	
}
