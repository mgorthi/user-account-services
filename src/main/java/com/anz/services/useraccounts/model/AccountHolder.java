package com.anz.services.useraccounts.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class AccountHolder {
	
	@Id
	@SequenceGenerator(name="account_holder_seq", sequenceName="account_holder_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="account_holder_seq")
	private Long id;
	
	@OneToMany(mappedBy = "accountHolder", cascade = CascadeType.ALL)
	private Set<AbstractAccount> accounts;
	
	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<AbstractAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<AbstractAccount> accounts) {
		this.accounts = accounts;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
