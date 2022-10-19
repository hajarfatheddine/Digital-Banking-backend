package com.digitalbanking.digitalbnaking_backend.repositories;

import com.digitalbanking.digitalbnaking_backend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
