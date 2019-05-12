package com.anz.services.useraccounts.service;

import java.util.List;

import com.anz.services.useraccounts.dto.AccountDto;
import com.anz.services.useraccounts.dto.TransactionDto;


public interface UserAccountService {

	public List<AccountDto> fetchAccounts(Long userId);

	public TransactionDto fetchTansactionsByUserAndAccount(Long userId, String accountId);

}
