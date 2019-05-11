package com.anz.services.useraccounts.service;

import com.anz.services.useraccounts.model.AccountBalance;

public interface AccountingService {

	AccountBalance fetchAccountBalance(String accountId);
}
