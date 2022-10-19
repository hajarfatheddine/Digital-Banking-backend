package com.digitalbanking.digitalbnaking_backend.services;

import com.digitalbanking.digitalbnaking_backend.entities.BankAccount;
import com.digitalbanking.digitalbnaking_backend.entities.CurrentAccount;
import com.digitalbanking.digitalbnaking_backend.entities.SavingAccount;
import com.digitalbanking.digitalbnaking_backend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount=bankAccountRepository.findById("28d067ea-9c85-42fd-a88b-17ba1755d69c").orElse(null);
        if (bankAccount !=null) {
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());
            if (bankAccount instanceof CurrentAccount) {
                System.out.println("Over Draft: " + ((CurrentAccount) bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("Interest rate: " + ((SavingAccount) bankAccount).getInterestRate());
            }
            bankAccount.getAccountOperations().forEach(op -> {
                System.out.println(op.getType() + " " + op.getOperationDate() + " " + op.getAmount());
            });
        }
    };
    }

