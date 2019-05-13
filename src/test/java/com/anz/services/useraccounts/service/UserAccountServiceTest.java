package com.anz.services.useraccounts.service;

import static com.anz.services.useraccounts.model.Account.AccountType.CURRENT;
import static com.anz.services.useraccounts.model.Account.AccountType.SAVINGS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.anz.services.useraccounts.dto.AccountDto;
import com.anz.services.useraccounts.dto.AccountDto.AccountDtoStatus;
import com.anz.services.useraccounts.dto.TransactionDto;
import com.anz.services.useraccounts.exception.InvalidUserException;
import com.anz.services.useraccounts.model.Account;
import com.anz.services.useraccounts.model.AccountBalance;
import com.anz.services.useraccounts.model.CurrentAccount;
import com.anz.services.useraccounts.model.SavingsAccount;
import com.anz.services.useraccounts.model.Transaction;
import com.anz.services.useraccounts.model.Transaction.TransactionType;
import com.anz.services.useraccounts.repository.UserAccountRepository;
import com.anz.services.useraccounts.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private UserAccountRepository userAccountRepository;
	
	@Mock
	private AccountingService accountingService;
	
	private UserAccountService userAccountService;
	
	@Before
	public void setup() {
		userAccountService = new UserAccountServiceImpl(userAccountRepository, userRepository, accountingService);
	}
	
	@Test
	public void testFetchAccountsShouldFetchAccountsForValidUser() throws InvalidUserException {
		final long userId = 1L;
		doAnswer(invocation -> true).when(userRepository).isUserActiveAccountHolder(userId);
		doAnswer(invocation -> {
			List<Account> accounts = new ArrayList<>();
			accounts.add(new SavingsAccount("1000", "AccountOne", Currency.getInstance("AUD")));
			accounts.add(new CurrentAccount("1002", "AccountTwo", Currency.getInstance("AUD")));
			accounts.add(new SavingsAccount("1003", "AccountThree", Currency.getInstance("AUD")));
			return accounts;
		}).when(userAccountRepository).findAllByUserId(userId);
		
		doAnswer(invocation -> new AccountBalance("100", LocalDate.of(2019, 01, 22))).when(accountingService).fetchAccountBalance("1000");
		doAnswer(invocation -> new AccountBalance("100", LocalDate.of(2019, 01, 22))).when(accountingService).fetchAccountBalance("1002");
		doAnswer(invocation -> new AccountBalance("100", LocalDate.of(2019, 01, 22))).when(accountingService).fetchAccountBalance("1003");
		
		List<AccountDto> fetchedAccounts = userAccountService.fetchAccounts(userId);
		
		assertThat(fetchedAccounts).isNotNull();
		assertThat(fetchedAccounts.size()).isEqualTo(3);
		assertThat(fetchedAccounts.stream().anyMatch(i -> i == null)).isFalse();
		assertThat(fetchedAccounts.get(0).getAccount().getAccountType()).isEqualTo(SAVINGS);
		assertThat(fetchedAccounts.get(0).getAccount().getAccountName()).isEqualTo("AccountOne");
		assertThat(fetchedAccounts.get(0).getAccount().getAccountNumber()).isEqualTo("1000");
		assertThat(fetchedAccounts.get(0).getAccountBalance()).isNotNull();
		assertThat(fetchedAccounts.get(0).getAccountBalance().getBalanceDate()).isEqualTo(LocalDate.of(2019, 01, 22));
		assertThat(fetchedAccounts.get(0).getAccount().getCurrency()).isEqualTo(Currency.getInstance("AUD"));
		assertThat(new BigDecimal(fetchedAccounts.get(0).getAccountBalance().getOpeningBalance())).isEqualTo(new BigDecimal("100"));
		
		assertThat(fetchedAccounts.get(1).getAccount().getAccountType()).isEqualTo(CURRENT);
		assertThat(fetchedAccounts.get(1).getAccount().getAccountName()).isEqualTo("AccountTwo");
		assertThat(fetchedAccounts.get(1).getAccount().getAccountNumber()).isEqualTo("1002");
		assertThat(fetchedAccounts.get(1).getAccountBalance()).isNotNull();
		assertThat(fetchedAccounts.get(1).getAccountBalance().getBalanceDate()).isEqualTo(LocalDate.of(2019, 01, 22));
		assertThat(fetchedAccounts.get(1).getAccount().getCurrency()).isEqualTo(Currency.getInstance("AUD"));
		assertThat(new BigDecimal(fetchedAccounts.get(1).getAccountBalance().getOpeningBalance())).isEqualTo(new BigDecimal("100"));
		
		assertThat(fetchedAccounts.get(2).getAccount().getAccountNumber()).isEqualTo("1003");
	}
	
	@Test
	public void testFetchAccountsShouldReturnEmptyForNoAccounts() throws InvalidUserException {
		final long userId = 1L;
		doAnswer(invocation -> true).when(userRepository).isUserActiveAccountHolder(userId);
		doAnswer(invocation -> Collections.emptyList()).when(userAccountRepository).findAllByUserId(userId);
		
		List<AccountDto> fetchedAccounts = userAccountService.fetchAccounts(userId);
		
		assertThat(fetchedAccounts).isNotNull();
		assertThat(fetchedAccounts.size()).isEqualTo(0);
	}
	
	@Test
	public void testAccountDtoStatusToBeErrorOnFailedFetchAccountsCall() throws InvalidUserException {
		final long userId = 1L;
		doAnswer(invocation -> true).when(userRepository).isUserActiveAccountHolder(userId);
		doAnswer(invocation -> {
			List<Account> accounts = new ArrayList<>();
			accounts.add(new SavingsAccount("1000", "AccountOne", Currency.getInstance("AUD")));
			accounts.add(new CurrentAccount("1002", "AccountTwo", Currency.getInstance("AUD")));
			return accounts;
		}).when(userAccountRepository).findAllByUserId(userId);
		
		doAnswer(invocation -> new AccountBalance("100", LocalDate.of(2019, 01, 22))).when(accountingService).fetchAccountBalance("1000");
		doThrow(new RuntimeException("failed fetching balance")).when(accountingService).fetchAccountBalance("1002");
		
		List<AccountDto> fetchedAccounts = userAccountService.fetchAccounts(userId);
		assertThat(fetchedAccounts).isNotNull();
		assertThat(fetchedAccounts.size()).isEqualTo(2);
		assertThat(fetchedAccounts.get(0).getStatus()).isEqualTo(AccountDtoStatus.SUCCESS);
		assertThat(fetchedAccounts.get(1).getStatus()).isEqualTo(AccountDtoStatus.ERROR);
	}
	
	@Test
	public void testFetchAccountsShouldThrowForNullUser() {
		try {
			userAccountService.fetchAccounts(null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("UserId cannot be empty");
		}
		
	}
	

	@Test
	public void testFetchAccountsShouldThrowForInActiveUser() {
		long userId = 100L;
		doAnswer(invocation -> false).when(userRepository).isUserActiveAccountHolder(userId);
		try {
			userAccountService.fetchAccounts(userId);
			Assert.fail();
		} catch (InvalidUserException e) {
			assertThat(e.getMessage()).isEqualTo("Invalid user");
		}
		
	}

	@Test
	public void testServiceShouldFetchTansactionsByAccountForValidUser() throws InvalidUserException {
		final long userId = 1L;
		final String accountId = "1000";
		doAnswer(invocation -> true).when(userRepository).isUserActiveAccountHolder(userId);
		doAnswer(invocation -> new SavingsAccount("1000", "AccountOne", Currency.getInstance("AUD"))).when(userAccountRepository).findByAccountNumber(accountId);
		
		doAnswer(invocation -> {
			List<Transaction> transactions = new ArrayList<>();
			transactions.add(new Transaction(new BigDecimal("100"), TransactionType.DEBIT, LocalDateTime.of(LocalDate.of(2019, 02, 05), LocalTime.of(13, 30)), ""));
			transactions.add(new Transaction(new BigDecimal("200"), TransactionType.CREDIT, LocalDateTime.of(LocalDate.of(2019, 03, 05), LocalTime.of(13, 30)), ""));
			transactions.add(new Transaction(new BigDecimal("300"), TransactionType.DEBIT, LocalDateTime.of(LocalDate.of(2019, 04, 05), LocalTime.of(13, 30)), ""));
			transactions.add(new Transaction(new BigDecimal("400"), TransactionType.CREDIT, LocalDateTime.of(LocalDate.of(2019, 05, 05), LocalTime.of(13, 30)), ""));
			return transactions;
		}).when(accountingService).fetchTransactions(userId, accountId);
		
		
		TransactionDto transactionDto = userAccountService.fetchTansactionsByUserAndAccount(userId, accountId);
		
		assertThat(transactionDto).isNotNull();
		assertThat(transactionDto.getAccount()).isNotNull();
		assertThat(transactionDto.getTransactions()).isNotNull();
		assertThat(transactionDto.getTransactions().size()).isEqualTo(4);
		assertThat(transactionDto.getTransactions().stream().anyMatch(i -> i == null)).isFalse();
		assertThat(transactionDto.getTransactions().get(0).getAmount()).isEqualTo(new BigDecimal("100"));
		assertThat(transactionDto.getTransactions().get(0).getTransactionDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 02, 05), LocalTime.of(13, 30)));
		assertThat(transactionDto.getTransactions().get(0).getTransactionType()).isEqualTo(TransactionType.DEBIT);
		assertThat(transactionDto.getTransactions().get(0).getNarrative()).isEqualTo("");
		assertThat(transactionDto.getAccount().getAccountName()).isEqualTo("AccountOne");
		assertThat(transactionDto.getAccount().getAccountNumber()).isEqualTo("1000");
		assertThat(transactionDto.getAccount().getCurrency()).isEqualTo(Currency.getInstance("AUD"));
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
