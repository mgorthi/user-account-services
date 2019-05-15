package com.anz.services.useraccounts.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Currency;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.anz.services.useraccounts.model.AccountHolder;
import com.anz.services.useraccounts.model.SavingsAccount;
import com.anz.services.useraccounts.model.Transaction;
import com.anz.services.useraccounts.model.TransactionType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void whenFindAllByUserId_thenReturnUserAccounts() {
    	AccountHolder accountHolder = new AccountHolder();
        accountHolder.setActive(true);
        accountHolder = entityManager.persist(accountHolder);
        
        SavingsAccount account = new SavingsAccount("1000", "AccountOne", Currency.getInstance("AUD"), accountHolder);
        entityManager.persist(account);
        Transaction t1 = new Transaction(new BigDecimal("100"), TransactionType.DEBIT, LocalDateTime.of(LocalDate.of(2019, 02, 05), LocalTime.of(13, 30)), "");
        t1.setAccount(account);
        Transaction t2 = new Transaction(new BigDecimal("101"), TransactionType.CREDIT, LocalDateTime.of(LocalDate.of(2019, 02, 05), LocalTime.of(13, 30)), "");
        t2.setAccount(account);
		entityManager.persist(t1);
		entityManager.persist(t2);
        entityManager.flush();
        
        List<Transaction> transactions = transactionRepository.findAllByAccountHolderAndAccountNumber(accountHolder.getId(), "1000");
        assertThat(transactions).isNotNull();
        assertThat(transactions.size()).isEqualTo(2);
    }
}
