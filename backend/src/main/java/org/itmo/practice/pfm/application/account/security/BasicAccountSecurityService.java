package org.itmo.practice.pfm.application.account.security;

import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.account.service.AccountService;
import org.itmo.practice.pfm.application.security.SecurityService;
import org.itmo.practice.pfm.domain.account.UserAccount;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("accountSecurity")
@RequiredArgsConstructor
public class BasicAccountSecurityService implements SecurityService<UUID> {

    private final AccountService service;

    @Override
    public boolean userHasAccessToView(UUID uuid, String userId) {
        Optional<UserAccount> accountOptional = service.findById(uuid);
        if (accountOptional.isEmpty()) {
            return true;
        }

        UserAccount account = accountOptional.get();

        return account.getUserId().equals(userId);
    }

    @Override
    public boolean userHasAccessToModify(UUID uuid, String userId) {
        return userHasAccessToView(uuid, userId);
    }
}
