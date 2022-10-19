package com.digitalbanking.digitalbnaking_backend.repositories;

import com.digitalbanking.digitalbnaking_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
