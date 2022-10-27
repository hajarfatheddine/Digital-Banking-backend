package com.digitalbanking.digitalbanking_backend.services;

import com.digitalbanking.digitalbanking_backend.dtos.AccountHistoryDTO;
import com.digitalbanking.digitalbanking_backend.dtos.AccountOperationDTO;
import com.digitalbanking.digitalbanking_backend.entities.AccountOperation;
import com.digitalbanking.digitalbanking_backend.entities.BankAccount;
import com.digitalbanking.digitalbanking_backend.enums.OperationType;
import com.digitalbanking.digitalbanking_backend.exceptions.BalanceNotSufficientException;
import com.digitalbanking.digitalbanking_backend.exceptions.BankAccountNotFoundException;
import com.digitalbanking.digitalbanking_backend.mappers.BankAccountMapperImpl;
import com.digitalbanking.digitalbanking_backend.repositories.AccountOperationRepository;
import com.digitalbanking.digitalbanking_backend.repositories.BankAccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
//this hepls us access the attribute log ogf Logger without declaring it
@Slf4j
public class OperationServiceImpl implements OperationService{
    private BankAccountService bankAccountService;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountRepository bankAccountRepository;
    private BankAccountMapperImpl bankAccountMapper;
    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("this bank account does not exist"));
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
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("this bank account does not exist"));
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
    public List<AccountOperationDTO> AccountHistory(String id){
        List<AccountOperation> byBankAccountId= accountOperationRepository.findByBankAccountId(id);
        return byBankAccountId.stream().map(op-> bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount == null){
            throw  new BankAccountNotFoundException("Account not found");
        }
        Page<AccountOperation> accountOperations= accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS= accountOperations.getContent().stream().map(op-> bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setPageSize(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }
}
