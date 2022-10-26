package com.digitalbanking.digitalbanking_backend.repositories;

import com.digitalbanking.digitalbanking_backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
