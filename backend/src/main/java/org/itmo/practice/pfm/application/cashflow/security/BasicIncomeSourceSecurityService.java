package org.itmo.practice.pfm.application.cashflow.security;

import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.cashflow.service.IncomeSourceService;
import org.itmo.practice.pfm.application.security.SecurityService;
import org.itmo.practice.pfm.domain.cashflow.IncomeSource;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("incomeSecurity")
@RequiredArgsConstructor
public class BasicIncomeSourceSecurityService implements SecurityService<UUID> {
    private final IncomeSourceService service;

    @Override
    public boolean userHasAccessToView(UUID id, String userId) {
        Optional<IncomeSource> incomeSourceOptional = service.findById(id);
        if(incomeSourceOptional.isEmpty()) {
            return true;
        }

        return incomeSourceOptional.get().getUserId().equals(userId);

    }

    @Override
    public boolean userHasAccessToModify(UUID id, String userId) {
        return userHasAccessToView(id, userId);
    }
}
