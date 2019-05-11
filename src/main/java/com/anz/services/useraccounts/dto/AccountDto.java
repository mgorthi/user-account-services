package com.anz.services.useraccounts.dto;

import com.anz.services.useraccounts.model.Account;
import com.anz.services.useraccounts.model.AccountBalance;

public final class AccountDto {
	
	private final Account account;
	private final AccountBalance accountBalance;
	
	private AccountDto(final Account account, final AccountBalance accountBalance) {
		this.account = account;
		this.accountBalance = accountBalance;
	}
	
	public Account getAccount() {
		return account;
	}

	public AccountBalance getAccountBalance() {
		return accountBalance;
	}

	public static class AccountDtoBuilder {
		
		private Account account;
		private AccountBalance accountBalance;

		private AccountDtoBuilder() {}
		
		public static AccountDtoBuilder getInstance() {
			return new AccountDtoBuilder();
		}
		
		public AccountDtoBuilder account(Account account) {
			this.account = account;
			return this;
		}
		
		public AccountDtoBuilder accountBalance(AccountBalance accountBalance) {
			this.accountBalance = accountBalance;
			return this;
		}
		
		public AccountDto build() {
			return new AccountDto(this.account, this.accountBalance);
		}
		
	}
}
