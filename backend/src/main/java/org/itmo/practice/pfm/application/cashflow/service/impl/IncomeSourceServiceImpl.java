package org.itmo.practice.pfm.application.cashflow.service.impl;

import org.itmo.practice.pfm.application.cashflow.service.IncomeSourceService;
import org.itmo.practice.pfm.infrastructure.cashflow.repository.IncomeSourceRepository;
import org.springframework.stereotype.Service;

@Service
public class IncomeSourceServiceImpl extends IncomeSourceService {
    private final IncomeSourceRepository repository;
    public IncomeSourceServiceImpl(IncomeSourceRepository repository) {
        super(repository);
        this.repository = repository;
    }

}
