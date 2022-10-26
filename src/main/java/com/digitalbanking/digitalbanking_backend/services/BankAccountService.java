package com.digitalbanking.digitalbanking_backend.services;

import com.digitalbanking.digitalbanking_backend.dtos.CustomerDTO;
import com.digitalbanking.digitalbanking_backend.entities.BankAccount;
import com.digitalbanking.digitalbanking_backend.entities.CurrentAccount;
import com.digitalbanking.digitalbanking_backend.entities.Customer;
import com.digitalbanking.digitalbanking_backend.entities.SavingAccount;
import com.digitalbanking.digitalbanking_backend.exceptions.BalanceNotSufficientException;
import com.digitalbanking.digitalbanking_backend.exceptions.BankAccountNotFoundException;
import com.digitalbanking.digitalbanking_backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CurrentAccount saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<BankAccount> bankAccountList();
}
