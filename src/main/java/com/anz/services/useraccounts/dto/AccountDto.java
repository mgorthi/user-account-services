package com.anz.services.useraccounts.dto;

import com.anz.services.useraccounts.model.Account;
import com.anz.services.useraccounts.model.AccountBalance;

public final class AccountDto {
	
	public enum AccountDtoStatus {
		SUCCESS, ERROR
	}
	
	private final Account account;
	private final AccountBalance accountBalance;
	private final AccountDtoStatus status;
	
	private AccountDto(final Account account, final AccountBalance accountBalance, final AccountDtoStatus status) {
		this.account = account;
		this.accountBalance = accountBalance;
		this.status = status;
	}
	
	public Account getAccount() {
		return account;
	}

	public AccountBalance getAccountBalance() {
		return accountBalance;
	}
	
	public AccountDtoStatus getStatus() {
		return status;
	}

	public static class AccountDtoBuilder {
		
		private Account account;
		private AccountBalance accountBalance;
		private AccountDtoStatus status;

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
			return new AccountDto(this.account, this.accountBalance, this.status);
		}

		public AccountDtoBuilder status(AccountDtoStatus status) {
			this.status = status;
			return this;
		}
		
		
	}
}
