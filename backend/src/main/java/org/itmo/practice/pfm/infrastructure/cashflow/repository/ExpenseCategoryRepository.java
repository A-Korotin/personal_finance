package org.itmo.practice.pfm.infrastructure.cashflow.repository;

import org.itmo.practice.pfm.domain.cashflow.ExpenseCategory;
import org.itmo.practice.pfm.infrastructure.repository.UserIdFilteringRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseCategoryRepository extends UserIdFilteringRepository<ExpenseCategory, UUID> {
}
