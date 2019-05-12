package com.anz.services.useraccounts.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.anz.services.useraccounts.model.Account;
import com.anz.services.useraccounts.model.Transaction;

public final class TransactionDto {
	
	private final Account account;
	private final List<Transaction> transactions;
	
	private TransactionDto(final Account account, final List<Transaction> transactions) {
		this.account = account;
		this.transactions = new ArrayList<>(transactions);
	}
	
	public Account getAccount() {
		return account;
	}

	public List<Transaction> getTransactions() {
		return Collections.unmodifiableList(transactions);
	}


	public static class TransactionDtoBuilder {
		
		private Account account;
		private List<Transaction> transactions;

		private TransactionDtoBuilder() {}
		
		public static TransactionDtoBuilder getInstance() {
			return new TransactionDtoBuilder();
		}
		
		public TransactionDtoBuilder account(Account account) {
			this.account = account;
			return this;
		}
		
		public TransactionDtoBuilder transactions(List<Transaction> transactions) {
			this.transactions = transactions;
			return this;
		}
		
		public TransactionDto build() {
			return new TransactionDto(this.account, this.transactions);
		}
		
	}
}
