package com.anz.services.useraccounts.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.anz.services.useraccounts.model.AccountHolder;

public interface AccountHolderRepository  extends CrudRepository<AccountHolder, Long> {
	
	@Query("select a.active "
			+ " from AccountHolder a "
			+ " where a.id=:id")
	Boolean isUserActiveAccountHolder(@Param("id") Long id);

}
