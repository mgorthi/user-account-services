package com.anz.services.useraccounts.service;

import java.util.List;

import com.anz.services.useraccounts.dto.AccountDto;
import com.anz.services.useraccounts.dto.TransactionDto;
import com.anz.services.useraccounts.exception.InvalidUserException;

public interface UserAccountService {

	public List<AccountDto> fetchAccounts(Long userId) throws InvalidUserException;

	public TransactionDto fetchTansactionsByUserAndAccount(Long userId, String accountId) throws InvalidUserException;

}
