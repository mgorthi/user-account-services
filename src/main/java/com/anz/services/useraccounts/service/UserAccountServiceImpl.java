package com.anz.services.useraccounts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.anz.services.useraccounts.dto.AccountDto;
import com.anz.services.useraccounts.dto.AccountDto.AccountDtoBuilder;
import com.anz.services.useraccounts.dto.AccountDto.AccountDtoStatus;
import com.anz.services.useraccounts.dto.TransactionDto;
import com.anz.services.useraccounts.dto.TransactionDto.TransactionDtoBuilder;
import com.anz.services.useraccounts.exception.InvalidUserException;
import com.anz.services.useraccounts.model.AccountBalance;
import com.anz.services.useraccounts.repository.UserAccountRepository;
import com.anz.services.useraccounts.repository.UserRepository;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	private UserRepository userRepository;
	private UserAccountRepository userAccountRepository;
	private AccountingService accountingService;
	
	@Autowired
	public UserAccountServiceImpl(final UserAccountRepository userAccountRepository, final UserRepository userRepository, final AccountingService accountingService) {
		this.userAccountRepository = userAccountRepository;
		this.userRepository = userRepository;
		this.accountingService = accountingService;
	}

	@Override
	public List<AccountDto> fetchAccounts(final Long userId) {
		
		if (userId == null) {
			throw new IllegalArgumentException("UserId cannot be empty");
		}
		
		if (!isActiveAccountHolder(userId)) {
			throw new InvalidUserException("Invalid user");
		}
		
		return userAccountRepository
			.findAllByUserId(userId)
			.stream()
			.parallel()
			.map(account -> {
				AccountBalance accountBalance = null;
				AccountDtoBuilder builder = AccountDtoBuilder.getInstance();
				try {
					accountBalance = accountingService.fetchAccountBalance(account.getAccountNumber());
					builder.accountBalance(accountBalance);
					builder.status(AccountDtoStatus.SUCCESS);
				} catch (Exception e) {
					builder.status(AccountDtoStatus.ERROR);
				}
				
				return builder
				.account(account)
				.build();
			}).collect(Collectors.toList());
	}

	@Override
	public TransactionDto fetchTansactionsByUserAndAccount(final Long userId, final String accountId) {
		
		if (userId == null || StringUtils.isEmpty(accountId)) {
			throw new IllegalArgumentException("UserId and accountId cannot be empty");
		}
		
		if (!isActiveAccountHolder(userId)) {
			throw new InvalidUserException("Invalid user");
		}
		
		return TransactionDtoBuilder
				.getInstance()
				.account(userAccountRepository.findByAccountNumber(accountId))
				.transactions(accountingService.fetchTransactions(userId, accountId))
				.build();
	}
	
	private boolean isActiveAccountHolder(Long userId) {
		return userRepository.isUserActiveAccountHolder(userId);
	}

}
