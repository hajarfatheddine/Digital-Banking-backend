package com.digitalbanking.digitalbanking_backend.services;

import com.digitalbanking.digitalbanking_backend.dtos.CustomerDTO;
import com.digitalbanking.digitalbanking_backend.entities.CurrentAccount;
import com.digitalbanking.digitalbanking_backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) ;
    List<CustomerDTO> ListCustomers();
    CustomerDTO customer(Long id) throws CustomerNotFoundException;

}
