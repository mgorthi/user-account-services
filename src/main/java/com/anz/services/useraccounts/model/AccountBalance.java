package com.anz.services.useraccounts.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

@Entity
public class AccountBalance {
	
	@Id
	@SequenceGenerator(name="account_balance_seq", sequenceName="account_balance_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="account_balance_seq")
	private Long id;
	
	@NotNull
	private BigDecimal openingBalance;
	
	@NotNull
	private LocalDate balanceDate;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "account_id", unique = true)
	private Account account;
	
	public AccountBalance(final String openingBalance, final LocalDate balanceDate) {
		Objects.requireNonNull(openingBalance, "balance cannot be null");
		Objects.requireNonNull(balanceDate, "balanceDate cannot be null");
		if (StringUtils.isEmpty(openingBalance)) {
			throw new IllegalArgumentException("Account balance cannot be empty");
		}
		this.openingBalance = new BigDecimal(openingBalance);
		this.balanceDate = balanceDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpeningBalance() {
		return openingBalance.toString();
	}

	public LocalDate getBalanceDate() {
		return balanceDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	public void setBalanceDate(LocalDate balanceDate) {
		this.balanceDate = balanceDate;
	}
	
}
