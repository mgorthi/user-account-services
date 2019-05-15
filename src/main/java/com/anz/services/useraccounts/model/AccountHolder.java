package com.anz.services.useraccounts.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class AccountHolder {
	
	@Id
	@SequenceGenerator(name="account_holder_seq", sequenceName="account_holder_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="account_holder_seq")
	private Long id;
	
	@OneToMany(mappedBy = "accountHolder", cascade = CascadeType.ALL)
	private Set<Account> accounts = new HashSet<>();
	
	@NotNull
	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Account> getAccounts() {
		return new HashSet<Account>(this.accounts);
	}

	public void addAccount(Account account) {
		if (this.accounts.contains(account))
			return;
		this.accounts.add(account);
		account.setAccountHolder(this);
	}

	public void removeAccount(Account account) {
		if (!this.accounts.contains(account))
			return;
		this.accounts.remove(account);
		account.setAccountHolder(null);
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
