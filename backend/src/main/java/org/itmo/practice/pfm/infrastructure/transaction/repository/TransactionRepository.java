package org.itmo.practice.pfm.infrastructure.transaction.repository;

import org.itmo.practice.pfm.domain.transaction.Transaction;
import org.itmo.practice.pfm.domain.transaction.TransactionType;
import org.itmo.practice.pfm.infrastructure.repository.UserIdFilteringRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends UserIdFilteringRepository<Transaction, UUID> {
    Page<Transaction> findAllByUserIdAndType(String userId, TransactionType type, Pageable pageable);
}
