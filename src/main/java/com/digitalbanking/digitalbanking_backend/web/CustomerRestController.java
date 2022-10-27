package com.digitalbanking.digitalbanking_backend.web;

import com.digitalbanking.digitalbanking_backend.dtos.CustomerDTO;
import com.digitalbanking.digitalbanking_backend.entities.Customer;
import com.digitalbanking.digitalbanking_backend.exceptions.CustomerNotFoundException;
import com.digitalbanking.digitalbanking_backend.services.BankAccountService;
import com.digitalbanking.digitalbanking_backend.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor
public class CustomerRestController {
    private CustomerService customerService;


    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return customerService.ListCustomers();
    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable Long id) throws CustomerNotFoundException {
    return customerService.customer(id);
    }

    @PostMapping("/customers")
    //@RequestBody c'est pour dire à spring que les données du customer vont etre recuperées en format json à partir du corps de la requete
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO request){
        return customerService.saveCustomer(request);
    }
    @PutMapping("/customers/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id,@RequestBody CustomerDTO request){
        request.setId(id);
        return customerService.updateCustomer(request);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }

}
