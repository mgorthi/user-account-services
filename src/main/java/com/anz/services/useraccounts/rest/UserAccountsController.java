package com.anz.services.useraccounts.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anz.services.useraccounts.dto.AccountDto;
import com.anz.services.useraccounts.dto.TransactionDto;
import com.anz.services.useraccounts.service.UserAccountService;

@RestController
@RequestMapping("/useraccounts/{userId}")
public class UserAccountsController {
	
	private final static Logger logger = LoggerFactory.getLogger(UserAccountsController.class);
	
	private UserAccountService userAccountService;
	
	public UserAccountsController(final UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}
	
	/**
     * Returns a a list of accounts held Account Holder with id userId
     *
     * @param   userId       an integer to be converted to an unsigned string.
     * @return  a list of accounts.
     */
	@GetMapping
	public List<AccountDto> findAccountsByUserId(@PathVariable final Long userId) {
		logger.info("fetching accounts for user {}", userId);
		return userAccountService.fetchAccounts(userId);
	}
	
	/**
     * Returns a a list of transactions and Account details of an Account with
     * id accountId held Account Holder with id userId
     *
     * @param   userId       an integer to be converted to an unsigned string.
     * @param   accountNumber   the radix to use in the string representation.
     * @return  a list of accounts.
     */
	@GetMapping
	@RequestMapping("/{accountNumber}")
	public TransactionDto findByAccountId(@PathVariable final Long userId, @PathVariable final String accountNumber) {
		logger.info("fetching transactions for user {} and account number {}", userId, accountNumber);
		return userAccountService.fetchTansactionsByUserAndAccount(userId, accountNumber);
	}

}
