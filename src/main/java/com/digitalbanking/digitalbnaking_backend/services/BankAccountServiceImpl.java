package com.digitalbanking.digitalbnaking_backend.services;

import com.digitalbanking.digitalbnaking_backend.entities.*;
import com.digitalbanking.digitalbnaking_backend.enums.OperationType;
import com.digitalbanking.digitalbnaking_backend.exceptions.BalanceNotSufficientException;
import com.digitalbanking.digitalbnaking_backend.exceptions.BankAccountNotFoundException;
import com.digitalbanking.digitalbnaking_backend.exceptions.CustomerNotFoundException;
import com.digitalbanking.digitalbnaking_backend.repositories.AccountOperationRepository;
import com.digitalbanking.digitalbnaking_backend.repositories.BankAccountRepository;
import com.digitalbanking.digitalbnaking_backend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
//this hepls us access the attribute log ogf Logger without declaring it
@Slf4j

public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    // l'objet qui permet de logger dans nos applications
    // Logger log= LoggerFactory.getLogger(this.getClass() .getName());
    @Override
    public Customer saveCustomer(Customer customer) {
        //pour logger un message on utilise log.info()
        log.info("Saving new customer");
        //il faut faire attention aux regles metier (l'existance du client, etc) mais dans notre cas on suppose que ces regles n'existent pas
        Customer savedCustomer= customerRepository.save(customer);
        return savedCustomer;
    }

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
    public List<Customer> ListCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException{
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("this bank account does not exist"));
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
       BankAccount bankAccount=getBankAccount(accountId);
       if(bankAccount.getBalance()<amount)
           throw new BalanceNotSufficientException("balance not sufficient");
       //creation d'une operation debit
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        //mise à  jour du solde
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=getBankAccount(accountId);
        //creation d'une operation debit
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        //mise à  jour du solde
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {

        debit(accountIdSource,amount,"Transfer to: "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from: "+ accountIdSource);
    }
    @Override
    public List<BankAccount> bankAccountList()
    {
        return bankAccountRepository.findAll();
    }
}
