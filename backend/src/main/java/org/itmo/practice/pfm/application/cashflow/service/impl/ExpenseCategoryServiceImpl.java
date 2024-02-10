package org.itmo.practice.pfm.application.cashflow.service.impl;

import org.itmo.practice.pfm.application.cashflow.service.ExpenseCategoryService;
import org.itmo.practice.pfm.infrastructure.cashflow.repository.ExpenseCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class ExpenseCategoryServiceImpl extends ExpenseCategoryService {
    private final ExpenseCategoryRepository repository;

    public ExpenseCategoryServiceImpl(ExpenseCategoryRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
