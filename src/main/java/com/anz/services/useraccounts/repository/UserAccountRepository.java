package com.anz.services.useraccounts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.anz.services.useraccounts.model.AbstractAccount;

public interface UserAccountRepository extends CrudRepository<AbstractAccount, Long> {
	
	@Query("select a "
			+ " from AbstractAccount a "
			+ " where a.accountHolder.id=:id")
	List<AbstractAccount> findAllByUserId(@Param("id") Long id);

	AbstractAccount findByAccountNumber(String accountNumber);

}
