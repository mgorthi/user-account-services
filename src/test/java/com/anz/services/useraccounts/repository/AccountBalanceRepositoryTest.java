package com.anz.services.useraccounts.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Currency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.anz.services.useraccounts.model.AccountBalance;
import com.anz.services.useraccounts.model.AccountHolder;
import com.anz.services.useraccounts.model.SavingsAccount;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountBalanceRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private AccountBalanceRepository accountBalanceRepository;

    @Test
    public void whenFindAllByUserId_thenReturnUserAccounts() {
    	AccountHolder accountHolder = new AccountHolder();
        accountHolder.setActive(true);
        accountHolder = entityManager.persist(accountHolder);
        
        SavingsAccount account = new SavingsAccount("1000", "AccountOne", Currency.getInstance("AUD"), accountHolder);
        entityManager.persist(account);
        
        AccountBalance balance = new AccountBalance("200", LocalDate.of(2019, 01, 22));
        balance.setAccount(account);
        entityManager.persist(balance);
        
        AccountBalance accountBalance = accountBalanceRepository.findByAccountAccountNumber(account.getAccountNumber());
        assertThat(accountBalance).isNotNull();
        assertThat(accountBalance.getOpeningBalance()).isEqualTo("200");
        assertThat(accountBalance.getBalanceDate()).isEqualTo(LocalDate.of(2019, 01, 22));
    }
}
