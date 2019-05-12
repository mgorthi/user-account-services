package com.anz.services.useraccounts.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.anz.services.useraccounts.dto.AccountDto;
import com.anz.services.useraccounts.dto.AccountDto.AccountDtoBuilder;
import com.anz.services.useraccounts.dto.AccountDto.AccountDtoStatus;
import com.anz.services.useraccounts.dto.TransactionDto.TransactionDtoBuilder;
import com.anz.services.useraccounts.exception.InvalidUserException;
import com.anz.services.useraccounts.model.AccountBalance;
import com.anz.services.useraccounts.model.CurrentAccount;
import com.anz.services.useraccounts.model.SavingsAccount;
import com.anz.services.useraccounts.model.Transaction;
import com.anz.services.useraccounts.model.Transaction.TransactionType;
import com.anz.services.useraccounts.service.UserAccountService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserAccountsController.class)
public class UserAccountsControllerTest {
	
	@Autowired
    private MockMvc mvc;
 
    @MockBean
    private UserAccountService userAccountService;
    
    @Test
    public void returnAccountsWhenFindAccountsByValidUserId() throws Exception {
		
        doAnswer(invocation -> {
        	AccountDto account1 = AccountDtoBuilder.getInstance()
        			.account(new SavingsAccount("1000", "AccountOne", Currency.getInstance("AUD")))
        			.accountBalance(new AccountBalance("100", LocalDate.of(2019, 01, 22)))
        			.status(AccountDtoStatus.SUCCESS)
        			.build();
        	AccountDto account2 = AccountDtoBuilder.getInstance()
        			.account(new CurrentAccount("1002", "AccountTwo", Currency.getInstance("AUD")))
        			.accountBalance(new AccountBalance("100", LocalDate.of(2019, 01, 22)))
        			.status(AccountDtoStatus.SUCCESS)
        			.build();
        	List<AccountDto> accounts = new ArrayList<>();
    		accounts.add(account1);
    		accounts.add(account2);
    		return accounts;
        }).when(userAccountService).fetchAccounts(1L);
     
        mvc.perform(get("/useraccounts/1")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(2)))
          .andExpect(jsonPath("$[0].account.accountNumber", is("1000")))
          .andExpect(jsonPath("$[1].account.accountNumber", is("1002")));
    }
    
    @Test
    public void returnEmptyAccountsWhenUserHasNoAccounts() throws Exception {
		
        doAnswer(invocation -> new ArrayList<>()).when(userAccountService).fetchAccounts(1L);
     
        mvc.perform(get("/useraccounts/1")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(0)));
    }
    
    @Test
    public void returnErrorResponseWhenInternalServerError() throws Exception {
		
        doThrow(new RuntimeException("Internal server error")).when(userAccountService).fetchAccounts(2L);
     
        mvc.perform(get("/useraccounts/2")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().is5xxServerError())
          .andExpect(jsonPath("$.errorcode", is(2)))
          .andExpect(jsonPath("$.message", is("Internal server error")));
    }
    
    @Test
    public void returnErrorResponseWhenInvalidUser() throws Exception {
		
        doThrow(new InvalidUserException("Invalid user")).when(userAccountService).fetchAccounts(1L);
     
        mvc.perform(get("/useraccounts/1")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().is4xxClientError())
          .andExpect(jsonPath("$.errorcode", is(1)))
          .andExpect(jsonPath("$.message", is("Invalid user")));
    }

    @Test
    public void returnTransactionsWhenFetchTansactionsByValidUserAndValidAccount() throws Exception {
		
        doAnswer(invocation -> {
        	List<Transaction> txns = new ArrayList<>();
        	txns.add(new Transaction(new BigDecimal("100"), TransactionType.DEBIT, LocalDateTime.of(LocalDate.of(2019, 02, 05), LocalTime.of(13, 30)), "debit 100"));
        	txns.add(new Transaction(new BigDecimal("100"), TransactionType.CREDIT, LocalDateTime.of(LocalDate.of(2019, 01, 05), LocalTime.of(13, 30)), "credit 100"));
        	return TransactionDtoBuilder.getInstance()
    				.account(new CurrentAccount("1002", "AccountTwo", Currency.getInstance("AUD")))
    				.transactions(txns)
    				.build();
        	
        }).when(userAccountService).fetchTansactionsByUserAndAccount(1L, "1000");
     
        mvc.perform(get("/useraccounts/1/1000")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.transactions", hasSize(2)))
          .andExpect(jsonPath("$.account.accountNumber", is("1002")))
          .andExpect(jsonPath("$.account.accountName", is("AccountTwo")))
          .andExpect(jsonPath("$.account.currency", is("AUD")))
          .andExpect(jsonPath("$.transactions[0].transactionType", is("DEBIT")))
          .andExpect(jsonPath("$.transactions[0].amount", is(100)))
          .andExpect(jsonPath("$.transactions[0].transactionDateTime", is("2019-02-05T13:30:00")))
          .andExpect(jsonPath("$.transactions[0].narrative", is("debit 100")))
          .andExpect(jsonPath("$.transactions[1].transactionType", is("CREDIT")))
          .andExpect(jsonPath("$.transactions[1].amount", is(100)))
          .andExpect(jsonPath("$.transactions[1].transactionDateTime", is("2019-01-05T13:30:00")))
          .andExpect(jsonPath("$.transactions[1].narrative", is("credit 100")));
    }
}
