package org.itmo.practice.pfm.application.cashflow.service;

import org.itmo.practice.pfm.application.service.CrudService;
import org.itmo.practice.pfm.domain.cashflow.ExpenseCategory;
import org.itmo.practice.pfm.infrastructure.repository.UserIdFilteringRepository;

import java.util.UUID;

public abstract class ExpenseCategoryService extends CrudService<ExpenseCategory, UUID> {
    public ExpenseCategoryService(UserIdFilteringRepository<ExpenseCategory, UUID> repository) {
        super(repository);
    }
}
