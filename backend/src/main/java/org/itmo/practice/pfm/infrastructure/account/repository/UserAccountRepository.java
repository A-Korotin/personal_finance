package org.itmo.practice.pfm.infrastructure.account.repository;

import org.itmo.practice.pfm.domain.account.UserAccount;
import org.itmo.practice.pfm.infrastructure.repository.UserIdFilteringRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserAccountRepository extends UserIdFilteringRepository<UserAccount, UUID> {
}
