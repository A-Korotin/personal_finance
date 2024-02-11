package org.itmo.practice.pfm.presentation.http.cashflow.mapper;

import org.itmo.practice.pfm.domain.cashflow.CashFlowName;
import org.itmo.practice.pfm.domain.cashflow.IncomeSource;
import org.itmo.practice.pfm.domain.funds.Money;
import org.itmo.practice.pfm.presentation.http.cashflow.dto.CashFlowDto;
import org.itmo.practice.pfm.presentation.http.cashflow.dto.OutputCashFlowDto;
import org.itmo.practice.pfm.presentation.http.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class IncomeSourceMapper implements EntityMapper<IncomeSource, CashFlowDto, OutputCashFlowDto> {
    @Override
    public IncomeSource fromDto(CashFlowDto dto, String userId) {
        return new IncomeSource(userId, new CashFlowName(dto.name), new Money(dto.expectedAmount, dto.currency));
    }

    @Override
    public OutputCashFlowDto toDto(IncomeSource entity) {
        return OutputCashFlowDto.builder()
                .id(entity.getId())
                .name(entity.getName().getName())
                .currency(entity.getMonthlyIncome().getCurrency())
                .expectedAmount(entity.getMonthlyIncome().getAmount())
                .userId(entity.getUserId())
                .build();
    }
}
