package org.itmo.practice.pfm.infrastructure.transaction.repository;

import org.itmo.practice.pfm.domain.transaction.IncomeTransaction;
import org.itmo.practice.pfm.infrastructure.repository.UserIdFilteringRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IncomeTransactionRepository extends UserIdFilteringRepository<IncomeTransaction, UUID> {
}
