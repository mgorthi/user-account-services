package com.anz.services.useraccounts.service;

import java.util.List;

import com.anz.services.useraccounts.model.AccountBalance;
import com.anz.services.useraccounts.model.Transaction;


public interface AccountingService {

	AccountBalance fetchAccountBalance(String accountId);

	List<Transaction> fetchTransactions(Long userId, String accountId);
}
