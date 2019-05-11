package com.anz.services.useraccounts.service;

import static com.anz.services.useraccounts.model.Account.AccountType.CURRENT;
import static com.anz.services.useraccounts.model.Account.AccountType.SAVINGS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.anz.services.useraccounts.exception.InvalidUserException;
import com.anz.services.useraccounts.model.Account;
import com.anz.services.useraccounts.model.AccountBalance;
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
			accounts.add(new SavingsAccount("1000", "AccountOne", new AccountBalance(100d, Currency.getInstance("AUD"), LocalDate.of(2019, 01, 22))));
			accounts.add(new CurrentAccount("1002", "AccountTwo", new AccountBalance(100d, Currency.getInstance("AUD"), LocalDate.of(2019, 01, 22))));
			accounts.add(new SavingsAccount("1003", "AccountThree", new AccountBalance(100d, Currency.getInstance("AUD"), LocalDate.of(2019, 01, 22))));
			return accounts;
		}).when(userAccountRepository).findAccountsByUserId(userId);
		
		List<Account> fetchedAccounts = userAccountService.fetchAccounts(userId);
		
		assertThat(fetchedAccounts).isNotNull();
		assertThat(fetchedAccounts.size()).isEqualTo(3);
		assertThat(fetchedAccounts.stream().anyMatch(i -> i == null)).isFalse();
		assertThat(fetchedAccounts.get(0).getAccountType()).isEqualTo(SAVINGS);
		assertThat(fetchedAccounts.get(0).getAccountName()).isEqualTo("AccountOne");
		assertThat(fetchedAccounts.get(0).getAccountNumber()).isEqualTo("1000");
		assertThat(fetchedAccounts.get(0).getAccountBalance()).isNotNull();
		assertThat(fetchedAccounts.get(0).getAccountBalance().getBalanceDate()).isEqualTo(LocalDate.of(2019, 01, 22));
		assertThat(fetchedAccounts.get(0).getAccountBalance().getCurrency()).isEqualTo(Currency.getInstance("AUD"));
		assertThat(new BigDecimal(fetchedAccounts.get(0).getAccountBalance().getOpeningBalance())).isEqualTo(new BigDecimal("100"));
		
		assertThat(fetchedAccounts.get(1).getAccountType()).isEqualTo(CURRENT);
		assertThat(fetchedAccounts.get(1).getAccountName()).isEqualTo("AccountTwo");
		assertThat(fetchedAccounts.get(1).getAccountNumber()).isEqualTo("1002");
		assertThat(fetchedAccounts.get(1).getAccountBalance()).isNotNull();
		assertThat(fetchedAccounts.get(1).getAccountBalance().getBalanceDate()).isEqualTo(LocalDate.of(2019, 01, 22));
		assertThat(fetchedAccounts.get(1).getAccountBalance().getCurrency()).isEqualTo(Currency.getInstance("AUD"));
		assertThat(new BigDecimal(fetchedAccounts.get(1).getAccountBalance().getOpeningBalance())).isEqualTo(new BigDecimal("100"));
		
		assertThat(fetchedAccounts.get(2).getAccountNumber()).isEqualTo("1003");
	}
	
	@Test
	public void testServiceShouldReturnEmptyForNoAccounts() throws InvalidUserException {
		final long userId = 1L;
		doAnswer(invocation -> true).when(userRepository).isUserActiveAccountHolder();
		doAnswer(invocation -> Collections.emptyList()).when(userAccountRepository).findAccountsByUserId(userId);
		
		List<Account> fetchedAccounts = userAccountService.fetchAccounts(userId);
		
		assertThat(fetchedAccounts).isNotNull();
		assertThat(fetchedAccounts.size()).isEqualTo(0);
	}
	
	@Test
	public void testServiceShouldThrowForNullUser() {
		try {
			userAccountService.fetchAccounts(null);
			Assert.fail();
		} catch (InvalidUserException e) {
			assertThat(e.getMessage()).isEqualTo("Invalid user");
		}
		
	}
	

	@Test
	public void testServiceShouldThrowForInActiveUser() {
		doAnswer(invocation -> false).when(userRepository).isUserActiveAccountHolder();
		try {
			userAccountService.fetchAccounts(100L);
			Assert.fail();
		} catch (InvalidUserException e) {
			assertThat(e.getMessage()).isEqualTo("Invalid user");
		}
		
	}
	/*
	@Test
	public void testServiceShouldReturnValidAccountTypes() {
		
		try {
			userAccountService.fetchAccounts(100L);
			Assert.fail();
		} catch (InvalidUserException e) {
			assertThat(e.getMessage()).isEqualTo("Invalid user");
		}
		
	}
	
	@Test
	public void testServiceShouldReturnOnlyMaxConfiguredRecords() {
		
	}*/

}
