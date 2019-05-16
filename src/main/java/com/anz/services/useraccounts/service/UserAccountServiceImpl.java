package com.anz.services.useraccounts.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.anz.services.useraccounts.model.AccountHolder;
import com.anz.services.useraccounts.repository.UserAccountRepository;
import com.anz.services.useraccounts.repository.AccountHolderRepository;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);
	
	private AccountHolderRepository accountHolderRepository;
	private UserAccountRepository userAccountRepository;
	private AccountingService accountingService;
	
	@Autowired
	public UserAccountServiceImpl(final UserAccountRepository userAccountRepository, final AccountHolderRepository accountHolderRepository, final AccountingService accountingService) {
		this.userAccountRepository = userAccountRepository;
		this.accountHolderRepository = accountHolderRepository;
		this.accountingService = accountingService;
	}

	/**
     * Returns a a list of accounts held Account Holder with id userId
     *
     * @param   accountHolderId the Account holder id.
     * @throws IllegalArgumentException if userId is null
     * @throws InvalidUserException if Account Holder is not active
     * @return  a list of accounts.
     */
	@Override
	public List<AccountDto> fetchAccounts(final Long accountHolderId) {
		
		if (accountHolderId == null) {
			logger.error("Account Holder id is null.");
			throw new IllegalArgumentException("UserId cannot be empty.");
		}
		
		if (!isActiveAccountHolder(accountHolderId)) {
			logger.error("Account holder is not active.");
			throw new InvalidUserException("Invalid user.");
		}
		
		return userAccountRepository
			.findAllByUserId(accountHolderId)
			.stream()
			.map(account -> {
				AccountBalance accountBalance = null;
				AccountDtoBuilder builder = AccountDtoBuilder.getInstance();
				try {
					accountBalance = accountingService.fetchAccountBalance(account.getAccountNumber());
					builder.accountBalance(accountBalance);
					builder.status(AccountDtoStatus.SUCCESS);
				} catch (Exception e) {
					logger.error("Failed fetching transaction for account number " +  account.getAccountNumber(),  e);
					builder.status(AccountDtoStatus.ERROR);
				}
				
				return builder
				.account(account)
				.build();
			}).collect(Collectors.toList());
	}

	/**
     * Returns a a list of transactions and Account details of an Account with
     * id accountId held Account Holder with id userId
     *
     * @param   accountHolderId        The Account Holder id.
     * @param   accountNumber The Account number of the Account.
     * @return  a list of accounts.
     * @throws IllegalArgumentException if userid is null or accountNumber is empty
     * @throws InvalidUserException if Account Holder is not active
     */
	@Override
	public TransactionDto fetchTansactionsByUserAndAccount(final Long accountHolderId, final String accountNumber) {
		
		if (accountHolderId == null || StringUtils.isEmpty(accountNumber)) {
			logger.error("Account Holder id is null OR Account number is empty.");
			throw new IllegalArgumentException("UserId and accountId cannot be empty");
		}
		
		if (!isActiveAccountHolder(accountHolderId)) {
			logger.error("Account holder is not active.");
			throw new InvalidUserException("Invalid user");
		}
		
		return TransactionDtoBuilder
				.getInstance()
				.account(userAccountRepository.findByAccountNumber(accountNumber))
				.transactions(accountingService.fetchTransactions(accountHolderId, accountNumber))
				.build();
	}
	
	private boolean isActiveAccountHolder(Long accountHolderId) {
		Optional<AccountHolder> accountHolder = accountHolderRepository.findById(accountHolderId);
		if (!accountHolder.isPresent()) {
			return false;
		}
		return accountHolder.get().isActive();
	}

}
