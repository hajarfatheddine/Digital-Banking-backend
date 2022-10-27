package com.digitalbanking.digitalbanking_backend.services;

import com.digitalbanking.digitalbanking_backend.dtos.BankAccountDTO;
import com.digitalbanking.digitalbanking_backend.dtos.CurrentBankAccountDTO;
import com.digitalbanking.digitalbanking_backend.dtos.SavingBankAccountDTO;
import com.digitalbanking.digitalbanking_backend.entities.BankAccount;
import com.digitalbanking.digitalbanking_backend.exceptions.BankAccountNotFoundException;
import com.digitalbanking.digitalbanking_backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CurrentBankAccountDTO saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<BankAccountDTO> bankAccountList();
}
