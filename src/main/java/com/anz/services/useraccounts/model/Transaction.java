package com.anz.services.useraccounts.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Transaction {
	
	public static enum TransactionType {
		DEBIT, CREDIT
	}
	
	@Id
	@SequenceGenerator(name="transaction_seq", sequenceName="transaction_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="transaction_seq")
	private Long id;
	
	private LocalDateTime transactionDateTime;
	
	private BigDecimal amount;
	
	private TransactionType transactionType;
	
	private String narrative;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private AbstractAccount account;
	
	public Transaction() {
		
	}
	
	public Transaction(final BigDecimal amount, final TransactionType transactionType, final LocalDateTime transactionDateTime, final String narrative) {
		this.transactionDateTime = transactionDateTime;
		this.amount = amount;
		this.transactionType = transactionType;
		this.narrative = narrative;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public AbstractAccount getAccount() {
		return account;
	}

	public void setAccount(AbstractAccount account) {
		this.account = account;
	}

	public void setTransactionDateTime(LocalDateTime transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
	
}
