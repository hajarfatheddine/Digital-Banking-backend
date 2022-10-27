package com.digitalbanking.digitalbanking_backend.web;


import com.digitalbanking.digitalbanking_backend.dtos.AccountHistoryDTO;
import com.digitalbanking.digitalbanking_backend.dtos.AccountOperationDTO;
import com.digitalbanking.digitalbanking_backend.dtos.BankAccountDTO;
import com.digitalbanking.digitalbanking_backend.exceptions.BankAccountNotFoundException;
import com.digitalbanking.digitalbanking_backend.services.BankAccountService;
import com.digitalbanking.digitalbanking_backend.services.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bank")
public class BankAccountRestController {
    private BankAccountService bankAccountService;
    private OperationService operationService;
//consultation d'un compte bancaire spécifique
    @GetMapping("/accounts/{id}")
    public BankAccountDTO getBankAccount(@PathVariable String id) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(id);
    }

//lister les comptes
    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){

        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
       return operationService.AccountHistory(accountId);
    }
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name="page",defaultValue ="0") int page,
                                               @RequestParam(name="size",defaultValue ="5") int size) throws BankAccountNotFoundException {
         return operationService.getAccountHistory(accountId,page,size);
    }
}


