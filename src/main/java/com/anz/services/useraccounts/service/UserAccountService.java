package com.anz.services.useraccounts.service;

import java.util.List;

import com.anz.services.useraccounts.exception.InvalidUserException;
import com.anz.services.useraccounts.model.Account;

public interface UserAccountService {

	public List<Account> fetchAccounts(Long userId) throws InvalidUserException;

}
