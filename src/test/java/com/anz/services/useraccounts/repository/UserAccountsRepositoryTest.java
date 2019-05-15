package com.anz.services.useraccounts.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Currency;
import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.anz.services.useraccounts.model.Account;
import com.anz.services.useraccounts.model.AccountHolder;
import com.anz.services.useraccounts.model.CurrentAccount;
import com.anz.services.useraccounts.model.SavingsAccount;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserAccountsRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void whenFindAllByUserId_thenReturnUserAccounts() {
    	AccountHolder accountHolder = new AccountHolder();
        accountHolder.setActive(true);
        accountHolder = entityManager.persist(accountHolder);
        
        entityManager.persist(new SavingsAccount("1000", "AccountOne", Currency.getInstance("AUD"), accountHolder));
        entityManager.persist(new CurrentAccount("1001", "AccountTwo", Currency.getInstance("AUD"), accountHolder));
        entityManager.flush();
        
        Iterable<Account> acounts = userAccountRepository.findAll();
        assertThat(acounts).isNotNull();
        assertThat(acounts).isNotEmpty();
        assertThat(StreamSupport.stream(acounts.spliterator(), false).count()).isEqualTo(2);
        
        List<Account> useracounts = userAccountRepository.findAllByUserId(accountHolder.getId());
        assertThat(useracounts).isNotNull();
        assertThat(useracounts).isNotEmpty();
        assertThat(useracounts.size()).isEqualTo(2);
        
    }
    
    @Test
    public void whenFindByAccountNumber_thenReturnUserAccount() {
    	AccountHolder accountHolder = new AccountHolder();
        accountHolder.setActive(true);
        accountHolder = entityManager.persist(accountHolder);
        entityManager.persist(new SavingsAccount("1000", "AccountOne", Currency.getInstance("AUD"), accountHolder));
        entityManager.flush();
        
        Account useracount = userAccountRepository.findByAccountNumber("1000");
        assertThat(useracount).isNotNull();
        assertThat(useracount.getAccountNumber()).isEqualTo("1000");
        assertThat(useracount.getAccountName()).isEqualTo("AccountOne");
    }
}
