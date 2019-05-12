package com.anz.services.useraccounts.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.anz.services.useraccounts.model.Account;

@Repository
public class UserAccountRepositoryImpl implements UserAccountRepository {
	
	@Override
	public List<Account> findAccountsByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Account findByAccountId(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

}
