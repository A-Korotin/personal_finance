package org.itmo.practice.pfm.application.account.service;

import org.itmo.practice.pfm.application.service.CrudService;
import org.itmo.practice.pfm.domain.account.UserAccount;
import org.itmo.practice.pfm.infrastructure.repository.UserIdFilteringRepository;

import java.util.UUID;

public abstract class AccountService extends CrudService<UserAccount, UUID> {
    public AccountService(UserIdFilteringRepository<UserAccount, UUID> repository) {
        super(repository);
    }

}
