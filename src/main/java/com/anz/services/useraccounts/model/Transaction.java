package com.anz.services.useraccounts.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class Transaction {
	
	public static enum TransactionType {
		DEBIT, CREDIT
	}
	
	private final LocalDateTime transactionDateTime;
	private final BigDecimal amount;
	private final TransactionType transactionType;
	private final String narrative;
	
	public Transaction(final BigDecimal amount, final TransactionType transactionType, final LocalDateTime transactionDateTime, final String narrative) {
		this.transactionDateTime = transactionDateTime;
		this.amount = amount;
		this.transactionType = transactionType;
		this.narrative = narrative;
	}

	public LocalDateTime getTransactionDateTime() {
		return transactionDateTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public String getNarrative() {
		return narrative;
	}
	
}
