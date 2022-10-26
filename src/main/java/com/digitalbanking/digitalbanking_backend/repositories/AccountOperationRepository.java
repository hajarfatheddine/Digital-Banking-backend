package com.digitalbanking.digitalbanking_backend.repositories;

import com.digitalbanking.digitalbanking_backend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
