package com.anz.services.useraccounts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anz.services.useraccounts.model.AccountBalance;
import com.anz.services.useraccounts.model.Transaction;

@Service
public class AccountingServiceImpl implements AccountingService {
	
	@Override
	public AccountBalance fetchAccountBalance(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Transaction> fetchTransactions(Long userId, String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

}
