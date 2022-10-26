package com.digitalbanking.digitalbanking_backend.repositories;

import com.digitalbanking.digitalbanking_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
