package com.digitalbanking.digitalbnaking_backend.exceptions;

public class CustomerNotFoundException extends Exception{
public  CustomerNotFoundException(String customer_not_found){
    super(customer_not_found);
}
}
