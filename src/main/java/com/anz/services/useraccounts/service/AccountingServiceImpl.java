package com.anz.services.useraccounts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anz.services.useraccounts.model.AccountBalance;
import com.anz.services.useraccounts.model.Transaction;
import com.anz.services.useraccounts.repository.AccountBalanceRepository;
import com.anz.services.useraccounts.repository.TransactionRepository;

@Service
public class AccountingServiceImpl implements AccountingService {
	
	private TransactionRepository transactionRepository;
	
	private AccountBalanceRepository accountBalanceRepository;
	
	public AccountingServiceImpl(TransactionRepository transactionRepository, AccountBalanceRepository accountBalanceRepository) {
		this.accountBalanceRepository = accountBalanceRepository;
		this.transactionRepository = transactionRepository;
	}
	
	@Override
	public AccountBalance fetchAccountBalance(String accountId) {
		return accountBalanceRepository.findByAccountAccountNumber(accountId);
	}
	
	@Override
	public List<Transaction> fetchTransactions(Long userId, String accountId) {
		return transactionRepository.findAllByAccountHolderAndAccountNumber(userId, accountId);
	}

}
