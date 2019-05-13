package com.anz.services.useraccounts.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.anz.services.useraccounts.model.AccountHolder;

public interface UserRepository  extends CrudRepository<AccountHolder, Long> {
	
	@Query("select a.active "
			+ " from AccountHolder a "
			+ " where a.id=:id")
	boolean isUserActiveAccountHolder(@Param("id") Long id);

}
