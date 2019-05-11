package com.anz.services.useraccounts.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.anz.services.useraccounts.exception.InvalidUserException;
import com.anz.services.useraccounts.model.Account;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {
	
	private UserAccountService userAccountService;
	
	@Before
	public void setup() {
		userAccountService = new UserAccountService();
	}
	
	@Test
	public void testServiceShouldFetchAccountsForValidUser() throws InvalidUserException {
		List<Account> fetchedAccounts = userAccountService.fetchAccounts("userIdOne");
		
		assertThat(fetchedAccounts).isNotNull();
		assertThat(fetchedAccounts.size()).isEqualTo(3);
	}
	
	@Test
	public void testServiceShouldReturnEmptyForNoAccounts() throws InvalidUserException {
		List<Account> fetchedAccounts = userAccountService.fetchAccounts("userIdTwo");
		
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
	public void testServiceShouldThrowForInValidUser() {
		
		try {
			userAccountService.fetchAccounts("invalidUser");
			Assert.fail();
		} catch (InvalidUserException e) {
			assertThat(e.getMessage()).isEqualTo("Invalid user");
		}
		
	}
	
	@Test
	public void testServiceShouldReturnOnlyMaxConfiguredRecords() {
		
	}

}
