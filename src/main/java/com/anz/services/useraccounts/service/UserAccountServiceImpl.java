package com.anz.services.useraccounts.service;

import java.util.List;
import java.util.stream.Collectors;

import com.anz.services.useraccounts.dto.AccountDto;
import com.anz.services.useraccounts.dto.AccountDto.AccountDtoBuilder;
import com.anz.services.useraccounts.exception.InvalidUserException;
import com.anz.services.useraccounts.repository.UserAccountRepository;
import com.anz.services.useraccounts.repository.UserRepository;

public class UserAccountServiceImpl implements UserAccountService {
	
	private UserRepository userRepository;
	private UserAccountRepository userAccountRepository;
	private AccountingService accountingService;
	
	public UserAccountServiceImpl(final UserAccountRepository userAccountRepository, final UserRepository userRepository, final AccountingService accountingService) {
		this.userAccountRepository = userAccountRepository;
		this.userRepository = userRepository;
		this.accountingService = accountingService;
	}

	@Override
	public List<AccountDto> fetchAccounts(final Long userId) throws InvalidUserException {
		if (userId == null || !isActiveAccountHolder(userId)) {
			throw new InvalidUserException("Invalid user");
		}
		
		return userAccountRepository
			.findAccountsByUserId(userId)
			.stream()
			.parallel()
			.map(account -> 
				AccountDtoBuilder.getInstance()
				.accountBalance(accountingService.fetchAccountBalance(account.getAccountNumber()))
				.account(account)
				.build()
			).collect(Collectors.toList());
	}

	private boolean isActiveAccountHolder(Long userId) {
		return userRepository.isUserActiveAccountHolder(userId);
	}

}
