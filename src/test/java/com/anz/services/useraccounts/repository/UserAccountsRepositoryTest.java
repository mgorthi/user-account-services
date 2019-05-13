package com.anz.services.useraccounts.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.anz.services.useraccounts.model.AbstractAccount;
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
    public void whenFindByName_thenReturnEmployee() {
        SavingsAccount savingsAccount = new SavingsAccount("1000", "AccountOne", Currency.getInstance("AUD"));
        CurrentAccount currentAccount = new CurrentAccount("1000", "AccountOne", Currency.getInstance("AUD"));
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setActive(true);
        accountHolder.setAccounts(new HashSet<>());
        accountHolder.getAccounts().add(currentAccount);
        accountHolder.getAccounts().add(savingsAccount);
        savingsAccount.setAccountHolder(accountHolder);
        currentAccount.setAccountHolder(accountHolder);
        accountHolder = entityManager.persist(accountHolder);
        entityManager.flush();
        Iterable<AbstractAccount> acounts = userAccountRepository.findAll();
        assertThat(StreamSupport.stream(acounts.spliterator(), false).count()).isEqualTo(2);
        List<AbstractAccount> useracounts = userAccountRepository.findAllByUserId(accountHolder.getId());
     
        assertThat(useracounts.size()).isEqualTo(2);
        
    }
}
