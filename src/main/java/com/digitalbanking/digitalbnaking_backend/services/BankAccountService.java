package com.digitalbanking.digitalbnaking_backend.services;

import com.digitalbanking.digitalbnaking_backend.entities.BankAccount;
import com.digitalbanking.digitalbnaking_backend.entities.CurrentAccount;
import com.digitalbanking.digitalbnaking_backend.entities.Customer;
import com.digitalbanking.digitalbnaking_backend.entities.SavingAccount;
import com.digitalbanking.digitalbnaking_backend.exceptions.BalanceNotSufficientException;
import com.digitalbanking.digitalbnaking_backend.exceptions.BankAccountNotFoundException;
import com.digitalbanking.digitalbnaking_backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    public Customer saveCustomer(Customer customer) ;
    CurrentAccount saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<Customer> ListCustomers();
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccount> bankAccountList();
}
