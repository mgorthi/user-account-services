package com.anz.services.useraccounts.service;

import java.util.List;

import com.anz.services.useraccounts.exception.InvalidUserException;
import com.anz.services.useraccounts.model.Account;
import com.anz.services.useraccounts.repository.UserAccountRepository;
import com.anz.services.useraccounts.repository.UserRepository;

public class UserAccountServiceImpl implements UserAccountService {
	
	private UserRepository userRepository;
	private UserAccountRepository userAccountRepository;
	
	public UserAccountServiceImpl(final UserAccountRepository userAccountRepository, final UserRepository userRepository) {
		this.userAccountRepository = userAccountRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<Account> fetchAccounts(final Long userId) throws InvalidUserException {
		if (userId == null || !isActiveAccountHolder(userId)) {
			throw new InvalidUserException("Invalid user ");
		}
		return userAccountRepository.findAccountsByUserId(userId);
	}

	private boolean isActiveAccountHolder(Long userId) {
		
		return userRepository.isUserActiveAccountHolder();
	}

}
