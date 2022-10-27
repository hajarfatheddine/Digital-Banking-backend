package com.digitalbanking.digitalbanking_backend.dtos;

import com.digitalbanking.digitalbanking_backend.entities.BankAccount;
import com.digitalbanking.digitalbanking_backend.enums.OperationType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
