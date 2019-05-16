package com.anz.services.useraccounts.service;

import java.util.List;

import com.anz.services.useraccounts.dto.AccountDto;
import com.anz.services.useraccounts.dto.TransactionDto;


public interface UserAccountService {

	/**
     * Returns a a list of accounts held Account Holder with id userId
     *
     * @param   userId the Account holder id.
     * @return  a list of accounts.
     */
	public List<AccountDto> fetchAccounts(Long userId);

	/**
     * Returns a a list of transactions and Account details of an Account with
     * id accountId held Account Holder with id userId
     *
     * @param   userId        The Account Holder id.
     * @param   accountNumber The Account number of the Account.
     * @return  a list of accounts.
     */
	public TransactionDto fetchTansactionsByUserAndAccount(Long userId, String accountId);

}
