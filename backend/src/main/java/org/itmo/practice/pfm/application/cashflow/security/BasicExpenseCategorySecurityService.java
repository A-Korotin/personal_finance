package org.itmo.practice.pfm.application.cashflow.security;

import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.cashflow.service.ExpenseCategoryService;
import org.itmo.practice.pfm.application.security.SecurityService;
import org.itmo.practice.pfm.domain.cashflow.ExpenseCategory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("expenseSecurity")
@RequiredArgsConstructor
public class BasicExpenseCategorySecurityService implements SecurityService<UUID> {
    private final ExpenseCategoryService service;


    @Override
    public boolean userHasAccessToView(UUID id, String userId) {
        Optional<ExpenseCategory> expenseCategoryOptional = service.findById(id);
        if(expenseCategoryOptional.isEmpty()) {
            return true;
        }

        return expenseCategoryOptional.get().getUserId().equals(userId);
    }

    @Override
    public boolean userHasAccessToModify(UUID uuid, String userId) {
        return userHasAccessToView(uuid, userId);
    }
}
