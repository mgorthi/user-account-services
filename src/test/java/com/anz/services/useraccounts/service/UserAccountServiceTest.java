package com.anz.services.useraccounts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.anz.services.useraccounts.exception.InvalidUserException;
import com.anz.services.useraccounts.model.Account;
import com.anz.services.useraccounts.model.CurrentAccount;
import com.anz.services.useraccounts.model.SavingsAccount;
import com.anz.services.useraccounts.repository.UserAccountRepository;
import com.anz.services.useraccounts.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private UserAccountRepository userAccountRepository;
	
	private UserAccountService userAccountService;
	
	@Before
	public void setup() {
		userAccountService = new UserAccountServiceImpl(userAccountRepository, userRepository);
	}
	
	@Test
	public void testServiceShouldFetchAccountsForValidUser() throws InvalidUserException {
		final long userId = 1L;
		doAnswer(invocation -> true).when(userRepository).isUserActiveAccountHolder();
		doAnswer(invocation -> {
			List<Account> accounts = new ArrayList<>();
			accounts.add(new SavingsAccount());
			accounts.add(new CurrentAccount());
			accounts.add(new SavingsAccount());
			return accounts;
		}).when(userAccountRepository).findAccountsByUserId(userId);
		
		
		List<Account> fetchedAccounts = userAccountService.fetchAccounts(userId);
		
		assertThat(fetchedAccounts).isNotNull();
		assertThat(fetchedAccounts.size()).isEqualTo(3);
	}
	

}
