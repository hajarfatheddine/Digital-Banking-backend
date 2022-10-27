package com.digitalbanking.digitalbanking_backend.services;

import com.digitalbanking.digitalbanking_backend.dtos.BankAccountDTO;
import com.digitalbanking.digitalbanking_backend.dtos.CurrentBankAccountDTO;

import com.digitalbanking.digitalbanking_backend.dtos.SavingBankAccountDTO;
import com.digitalbanking.digitalbanking_backend.entities.*;
import com.digitalbanking.digitalbanking_backend.exceptions.BankAccountNotFoundException;
import com.digitalbanking.digitalbanking_backend.exceptions.CustomerNotFoundException;
import com.digitalbanking.digitalbanking_backend.mappers.BankAccountMapperImpl;

import com.digitalbanking.digitalbanking_backend.repositories.BankAccountRepository;
import com.digitalbanking.digitalbanking_backend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j

public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private BankAccountMapperImpl bankAccountMapper;

    @Override
    public CurrentBankAccountDTO saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {

            Customer customer=customerRepository.findById(customerId).orElse(null);
            if(customer==null){
                throw new CustomerNotFoundException("Customer Not Found");
            }
            CurrentAccount currentAccount=new CurrentAccount();
            currentAccount.setId(UUID.randomUUID().toString());
            currentAccount.setCreatedAt(new Date());
            currentAccount.setBalance(initialBalance);
            currentAccount.setOverDraft(overDraft);
            currentAccount.setCustomer(customer);
            CurrentAccount savedBankAccount= bankAccountRepository.save(currentAccount);
            log.info("Current Account Saved 111");
            return bankAccountMapper.fromCurrentBankAccount(savedBankAccount);

    }

    @Override
    public SavingBankAccountDTO saveSavingAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {

        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new CustomerNotFoundException("Customer Not Found");
        }

        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedBankAccount= bankAccountRepository.save(savingAccount);
        log.info("Saving Account Saved 222");
        return bankAccountMapper.fromSavingBankAccount(savedBankAccount);
    }




    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException{
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("this bank account does not exist"));
        if(bankAccount instanceof CurrentAccount) {
            CurrentAccount currentAccount=(CurrentAccount) bankAccount;
            return bankAccountMapper.fromCurrentBankAccount(currentAccount);
        }
        else {
            SavingAccount savingAccount=(SavingAccount) bankAccount;
            return bankAccountMapper.fromSavingBankAccount(savingAccount);
        }

    }


    @Override
    public List<BankAccountDTO> bankAccountList()
    {
        List<BankAccount> bankAccountList= bankAccountRepository.findAll();
        return bankAccountList.stream().map(bankAccount -> {
           if(bankAccount instanceof CurrentAccount){
               CurrentAccount currentAccount=(CurrentAccount) bankAccount;
               return bankAccountMapper.fromCurrentBankAccount(currentAccount);
           }
           else {
               SavingAccount savingAccount=(SavingAccount) bankAccount;
               return bankAccountMapper.fromSavingBankAccount(savingAccount);
           }
        }).collect(Collectors.toList());

    }


}
