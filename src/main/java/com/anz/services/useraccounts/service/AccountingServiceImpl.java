package com.anz.services.useraccounts.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.anz.services.useraccounts.model.AccountBalance;
import com.anz.services.useraccounts.model.Transaction;
import com.anz.services.useraccounts.repository.AccountBalanceRepository;
import com.anz.services.useraccounts.repository.TransactionRepository;

@Service
public class AccountingServiceImpl implements AccountingService {
	
	private final static Logger logger = LoggerFactory.getLogger(AccountingServiceImpl.class);
	
	private TransactionRepository transactionRepository;
	
	private AccountBalanceRepository accountBalanceRepository;
	
	public AccountingServiceImpl(TransactionRepository transactionRepository, AccountBalanceRepository accountBalanceRepository) {
		this.accountBalanceRepository = accountBalanceRepository;
		this.transactionRepository = transactionRepository;
	}
	
	@Override
	public AccountBalance fetchAccountBalance(String accountId) {
		logger.info("Fetching account balance for account {}", accountId);
		return accountBalanceRepository.findByAccountAccountNumber(accountId);
	}
	
	@Override
	public List<Transaction> fetchTransactions(Long accountHolderId, String accountId) {
		logger.info("Fetching transactions for accountHolderId {} and for accountId {}", accountHolderId, accountId);
		return transactionRepository.findAllByAccountHolderAndAccountNumber(accountHolderId, accountId);
	}

}
