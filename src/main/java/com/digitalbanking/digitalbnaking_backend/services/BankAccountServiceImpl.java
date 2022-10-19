package com.digitalbanking.digitalbnaking_backend.services;

import com.digitalbanking.digitalbnaking_backend.entities.BankAccount;
import com.digitalbanking.digitalbnaking_backend.entities.CurrentAccount;
import com.digitalbanking.digitalbnaking_backend.entities.Customer;
import com.digitalbanking.digitalbnaking_backend.entities.SavingAccount;
import com.digitalbanking.digitalbnaking_backend.exceptions.CustomerNotFoundException;
import com.digitalbanking.digitalbnaking_backend.repositories.AccountOperationRepository;
import com.digitalbanking.digitalbnaking_backend.repositories.BankAccountRepository;
import com.digitalbanking.digitalbnaking_backend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public BankAccount saveBankAccount(double initialBalance, String type, Long customerId)  throws CustomerNotFoundException{
        BankAccount bankAccount;
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new CustomerNotFoundException("Customer Not Found");
        }
        if (type.equals("current")){
            bankAccount= new CurrentAccount();
        }else{
            bankAccount=new SavingAccount();
        }
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);

        return null;
    }

    @Override
    public List<Customer> ListCustomers() {
        return null;
    }

    @Override
    public BankAccount getBankAccount(String accountId) {
        return null;
    }

    @Override
    public void debit(String accountId, double amount, String description) {

    }

    @Override
    public void credit(String accountId, double amount, String description) {

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) {

    }
}
