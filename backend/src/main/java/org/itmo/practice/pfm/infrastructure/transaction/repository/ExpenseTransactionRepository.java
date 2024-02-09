package org.itmo.practice.pfm.infrastructure.transaction.repository;

import org.itmo.practice.pfm.domain.transaction.ExpenseTransaction;
import org.itmo.practice.pfm.infrastructure.repository.UserIdFilteringRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpenseTransactionRepository extends UserIdFilteringRepository<ExpenseTransaction, UUID> {
}
