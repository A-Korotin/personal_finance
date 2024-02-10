package org.itmo.practice.pfm.application.cashflow.service;

import org.itmo.practice.pfm.application.service.CrudService;
import org.itmo.practice.pfm.domain.cashflow.IncomeSource;
import org.itmo.practice.pfm.infrastructure.repository.UserIdFilteringRepository;

import java.util.UUID;

public abstract class IncomeSourceService extends CrudService<IncomeSource, UUID> {
    public IncomeSourceService(UserIdFilteringRepository<IncomeSource, UUID> repository) {
        super(repository);
    }
}
