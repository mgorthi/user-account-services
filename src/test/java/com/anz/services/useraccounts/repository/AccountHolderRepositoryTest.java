package com.anz.services.useraccounts.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.anz.services.useraccounts.model.AccountHolder;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountHolderRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private AccountHolderRepository userRepository;

    @Test
    public void whenFindAllByUserId_thenReturnUserAccounts() {
    	AccountHolder accountHolder = new AccountHolder();
        accountHolder.setActive(true);
        accountHolder = entityManager.persist(accountHolder);
        
        Boolean active = userRepository.isUserActiveAccountHolder(accountHolder.getId());
        assertThat(active).isNotNull();
        assertTrue(active);
        
    }
}
