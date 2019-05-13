package com.anz.services.useraccounts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.anz.services.useraccounts.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	@Query("select t "
			+ " from Transaction t "
			+ " where t.account.accountNumber=:accountNumber"
			+ " and t.account.accountHolder.id=:userId")
	List<Transaction> findAllByAccountHolderAndAccountNumber(@Param("userId") Long userId, @Param("accountNumber") String accountNumber);

}
