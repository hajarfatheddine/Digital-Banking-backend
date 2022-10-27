package com.digitalbanking.digitalbanking_backend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDTO {
    //réglage de la pagination
    private int currentPage;
    private int totalPages;
    private  int pageSize;
    private String accountId;
    private double balance;
    private List<AccountOperationDTO> accountOperationDTOS;

}
