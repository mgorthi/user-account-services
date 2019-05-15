package com.anz.services.useraccounts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.anz.services.useraccounts.model.Account;

public interface UserAccountRepository extends CrudRepository<Account, Long> {
	
	@Query("select a "
			+ " from Account a "
			+ " where a.accountHolder.id=:id")
	List<Account> findAllByUserId(@Param("id") Long id);

	Account findByAccountNumber(String accountNumber);

}
