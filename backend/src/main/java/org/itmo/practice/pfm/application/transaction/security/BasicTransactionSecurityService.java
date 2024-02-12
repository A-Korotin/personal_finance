package org.itmo.practice.pfm.application.transaction.security;

import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.security.SecurityService;
import org.itmo.practice.pfm.application.transaction.service.TransactionService;
import org.itmo.practice.pfm.domain.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("transactionSecurity")
@RequiredArgsConstructor
public class BasicTransactionSecurityService implements SecurityService<UUID> {
    private final TransactionService transactionService;

    @Override
    public boolean userHasAccessToView(UUID id, String userId) {
        Optional<? extends Transaction> transactionOptional = transactionService.findById(id);
        if (transactionOptional.isEmpty()) {
            return true;
        }

        return transactionOptional.get().getUserId().equals(userId);
    }

    @Override
    public boolean userHasAccessToModify(UUID id, String userId) {
        return userHasAccessToView(id, userId);
    }
}
