package com.anz.services.useraccounts.repository;

import java.util.List;

import com.anz.services.useraccounts.model.Account;

public interface UserAccountRepository {

	List<Account> findAccountsByUserId(Long userId);

}
