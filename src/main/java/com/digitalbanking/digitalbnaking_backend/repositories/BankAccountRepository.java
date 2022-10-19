package com.digitalbanking.digitalbnaking_backend.repositories;

import com.digitalbanking.digitalbnaking_backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
