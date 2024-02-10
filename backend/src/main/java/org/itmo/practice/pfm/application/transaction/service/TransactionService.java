package org.itmo.practice.pfm.application.transaction.service;

import org.itmo.practice.pfm.domain.transaction.Transaction;
import org.itmo.practice.pfm.domain.transaction.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TransactionService {
    Transaction save(Transaction transaction);
    Page<? extends Transaction> findAllByUserId(String userId, Pageable pageable);
    Page<? extends Transaction> findAllByUserId(String userId, TransactionType type, Pageable pageable);
    Optional<? extends Transaction> findById(UUID id);
    void revert(UUID id);
}
