package com.digitalbanking.digitalbanking_backend.services;

import com.digitalbanking.digitalbanking_backend.dtos.CustomerDTO;
import com.digitalbanking.digitalbanking_backend.entities.CurrentAccount;
import com.digitalbanking.digitalbanking_backend.entities.Customer;
import com.digitalbanking.digitalbanking_backend.exceptions.CustomerNotFoundException;
import com.digitalbanking.digitalbanking_backend.mappers.BankAccountMapperImpl;
import com.digitalbanking.digitalbanking_backend.repositories.AccountOperationRepository;
import com.digitalbanking.digitalbanking_backend.repositories.BankAccountRepository;
import com.digitalbanking.digitalbanking_backend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
//this hepls us access the attribute log ogf Logger without declaring it
@Slf4j
public class CustomerServiceImpl implements CustomerService{
    private CustomerRepository customerRepository;
    private BankAccountMapperImpl bankAccountMapper;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        //pour logger un message on utilise log.info()
        log.info("Saving new customer");
        //il faut faire attention aux regles metier (l'existance du client, etc) mais dans notre cas on suppose que ces regles n'existent pas
        Customer customer=bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer= customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer);
    }
    @Override
    public List<CustomerDTO> ListCustomers() {

        List<Customer> customerList= customerRepository.findAll();
        List<CustomerDTO>customerDTOList= customerList.stream().map(customer -> bankAccountMapper.fromCustomer(customer)).collect(Collectors.toList());
        //another way to code this is
        /*
        List<CustomerDTO> customerDTOList=new ArrayList<>();
        for (Customer customer:customerList){
            CustomerDTO customerDTO=bankAccountMapper.fromCustomer(customer);
            customerDTOList.add(customerDTO);
        }
        */
        return customerDTOList;
    }
    @Override
    public CustomerDTO customer(Long id) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
        return bankAccountMapper.fromCustomer(customer);
    }



}
