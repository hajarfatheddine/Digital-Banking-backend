package com.digitalbanking.digitalbanking_backend.services;

import com.digitalbanking.digitalbanking_backend.dtos.CustomerDTO;
import com.digitalbanking.digitalbanking_backend.entities.*;
import com.digitalbanking.digitalbanking_backend.enums.OperationType;
import com.digitalbanking.digitalbanking_backend.exceptions.BalanceNotSufficientException;
import com.digitalbanking.digitalbanking_backend.exceptions.BankAccountNotFoundException;
import com.digitalbanking.digitalbanking_backend.exceptions.CustomerNotFoundException;
import com.digitalbanking.digitalbanking_backend.mappers.BankAccountMapperImpl;
import com.digitalbanking.digitalbanking_backend.repositories.AccountOperationRepository;
import com.digitalbanking.digitalbanking_backend.repositories.BankAccountRepository;
import com.digitalbanking.digitalbanking_backend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
//this hepls us access the attribute log ogf Logger without declaring it
@Slf4j

public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    // l'objet qui permet de logger dans nos applications
    // Logger log= LoggerFactory.getLogger(this.getClass() .getName());

    private BankAccountMapperImpl bankAccountMapper;

    @Override
    public CurrentAccount saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {

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

            return savedBankAccount;

    }

    @Override
    public SavingAccount saveSavingAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {

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

        return savedBankAccount;
    }




    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException{
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("this bank account does not exist"));
        return bankAccount;
    }


    @Override
    public List<BankAccount> bankAccountList()
    {
        return bankAccountRepository.findAll();
    }


}
