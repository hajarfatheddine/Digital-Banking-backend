package com.digitalbanking.digitalbnaking_backend;

import com.digitalbanking.digitalbnaking_backend.entities.*;
import com.digitalbanking.digitalbnaking_backend.enums.AccountStatus;
import com.digitalbanking.digitalbnaking_backend.enums.OperationType;
import com.digitalbanking.digitalbnaking_backend.exceptions.BalanceNotSufficientException;
import com.digitalbanking.digitalbnaking_backend.exceptions.BankAccountNotFoundException;
import com.digitalbanking.digitalbnaking_backend.exceptions.CustomerNotFoundException;
import com.digitalbanking.digitalbnaking_backend.repositories.AccountOperationRepository;
import com.digitalbanking.digitalbnaking_backend.repositories.BankAccountRepository;
import com.digitalbanking.digitalbnaking_backend.repositories.CustomerRepository;
import com.digitalbanking.digitalbnaking_backend.services.BankAccountService;
import com.digitalbanking.digitalbnaking_backend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBnakingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBnakingBackendApplication.class, args);
    }
@Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Hassan", "Yassine" , "Aicha").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.ListCustomers().forEach(c->{
                try {
                    bankAccountService.saveCurrentAccount(Math.random()*90000,9000,c.getId());
                    bankAccountService.saveSavingAccount(Math.random()*12000,5.5,c.getId());
                    List<BankAccount> bankAccounts= bankAccountService.bankAccountList();
                    for(BankAccount bankAccount:bankAccounts) {
                        //pour chaque compte on crée un credit
                        for (int i = 0; i < 10; i++) {
                            bankAccountService.credit(bankAccount.getId(), Math.random() * 120000, "credit");
                            bankAccountService.debit(bankAccount.getId(), 1000 + Math.random() * 9000, "debit");

                        }
                    }

                } catch (CustomerNotFoundException | BankAccountNotFoundException | BalanceNotSufficientException e) {
                    e.getStackTrace();
                }
            });
        };
    }

    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Hassan", "Yassine" , "Aicha").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(c->{
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(c);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(c);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(account->{
                for (int i=0; i<10 ; i++){
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(account);
                    accountOperationRepository.save(accountOperation);
                }

            });
        };


    }

}
