package com.anz.services.useraccounts.rest;

import java.util.List;

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
	
	private UserAccountService userAccountService;
	
	public UserAccountsController(final UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}
	
	@GetMapping
	public List<AccountDto> findAccountsByUserId(@PathVariable final Long userId) {
		return userAccountService.fetchAccounts(userId);
	}
	
	@GetMapping
	@RequestMapping("/{accountId}")
	public TransactionDto findByAccountId(@PathVariable final Long userId, @PathVariable final String accountId) {
		return userAccountService.fetchTansactionsByUserAndAccount(userId, accountId);
	}

}
