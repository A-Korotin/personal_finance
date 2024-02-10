package org.itmo.practice.pfm.application.account.service.impl;

import org.itmo.practice.pfm.application.account.service.AccountService;
import org.itmo.practice.pfm.domain.account.UserAccount;
import org.itmo.practice.pfm.infrastructure.account.repository.UserAccountRepository;
import org.itmo.practice.pfm.infrastructure.repository.UserIdFilteringRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountServiceImpl extends AccountService {
    private final UserAccountRepository repository;

    public AccountServiceImpl(UserAccountRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
