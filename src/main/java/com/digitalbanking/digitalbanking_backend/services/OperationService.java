package com.digitalbanking.digitalbanking_backend.services;

import com.digitalbanking.digitalbanking_backend.dtos.AccountHistoryDTO;
import com.digitalbanking.digitalbanking_backend.dtos.AccountOperationDTO;
import com.digitalbanking.digitalbanking_backend.exceptions.BalanceNotSufficientException;
import com.digitalbanking.digitalbanking_backend.exceptions.BankAccountNotFoundException;

import java.util.List;

public interface OperationService {
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<AccountOperationDTO> AccountHistory(String id);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
