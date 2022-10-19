package com.digitalbanking.digitalbnaking_backend.services;

import com.digitalbanking.digitalbnaking_backend.entities.BankAccount;
import com.digitalbanking.digitalbnaking_backend.entities.Customer;

import java.util.List;

public interface BankAccountService {
    public Customer saveCustomer(Customer customer) ;
    BankAccount saveBankAccount(double initialBalance, String type, Long customerId);
    List<Customer> ListCustomers();
    BankAccount getBankAccount(String accountId);
    void debit(String accountId, double amount, String description);
    void credit(String accountId, double amount, String description);
    void transfer(String accountIdSource, String accountIdDestination, double amount);


}
