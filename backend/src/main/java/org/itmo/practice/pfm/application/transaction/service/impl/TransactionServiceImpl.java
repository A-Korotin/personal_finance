package org.itmo.practice.pfm.application.transaction.service.impl;

import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.transaction.service.TransactionService;
import org.itmo.practice.pfm.domain.transaction.Transaction;
import org.itmo.practice.pfm.domain.transaction.TransactionType;
import org.itmo.practice.pfm.infrastructure.transaction.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;

    @Override
    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public Page<? extends Transaction> findAllByUserId(String userId, Pageable pageable) {
        return repository.findAllByUserId(userId, pageable);
    }

    @Override
    public Page<? extends Transaction> findAllByUserId(String userId, TransactionType type, Pageable pageable) {
        return repository.findAllByUserIdAndType(userId, type, pageable);
    }

    @Override
    public Optional<? extends Transaction> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public void revert(UUID id) {
        Transaction transaction = findById(id).orElseThrow();
        transaction.revert();
        repository.save(transaction);
    }
}
