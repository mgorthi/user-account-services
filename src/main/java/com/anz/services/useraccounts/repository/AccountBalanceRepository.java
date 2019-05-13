package com.anz.services.useraccounts.repository;

import org.springframework.data.repository.CrudRepository;

import com.anz.services.useraccounts.model.AccountBalance;

public interface AccountBalanceRepository extends CrudRepository<AccountBalance, Long> {

	AccountBalance findByAccountAccountNumber(String id);

}
