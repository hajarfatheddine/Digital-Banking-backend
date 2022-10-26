package com.digitalbanking.digitalbanking_backend.services;

import com.digitalbanking.digitalbanking_backend.exceptions.BalanceNotSufficientException;
import com.digitalbanking.digitalbanking_backend.exceptions.BankAccountNotFoundException;

public interface OperationService {
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
}
