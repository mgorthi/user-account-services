package com.anz.services.useraccounts.model;

import java.util.Currency;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
	
	@Id
	@SequenceGenerator(name="account_seq", sequenceName="account_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="account_seq")
	private Long id;
	
	@NotNull
	@Column(unique=true)
	private String accountNumber;
	
	@NotNull
	private String accountName;
	
	@NotNull
	private Currency currency;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id")
	private AccountHolder accountHolder;
	
	public Account() {
		
	}
	
	protected Account(final String accountNumber, final String accountName, final Currency currency, AccountHolder accountHolder) {
		Objects.requireNonNull(accountNumber, "accountNumber cannot be null");
		Objects.requireNonNull(accountName, "accountName cannot be null");
		Objects.requireNonNull(currency, "currency cannot be null");
		Objects.requireNonNull(accountHolder, "accountHolder cannot be null");
		Objects.requireNonNull(accountHolder.getId(), "accountHolder should be valid");
		this.accountNumber = accountNumber;
		this.accountName = accountName;
		this.currency = currency;
		this.accountHolder = accountHolder;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public Currency getCurrency() {
		return currency;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public AccountHolder getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}

	public abstract AccountType getAccountType();
	
}
