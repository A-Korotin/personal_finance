package org.itmo.practice.pfm.presentation.http.cashflow.mapper;

import org.itmo.practice.pfm.domain.cashflow.CashFlowName;
import org.itmo.practice.pfm.domain.cashflow.ExpenseCategory;
import org.itmo.practice.pfm.domain.funds.Money;
import org.itmo.practice.pfm.presentation.http.cashflow.dto.CashFlowDto;
import org.itmo.practice.pfm.presentation.http.cashflow.dto.OutputCashFlowDto;
import org.itmo.practice.pfm.presentation.http.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCategoryMapper implements EntityMapper<ExpenseCategory, CashFlowDto, OutputCashFlowDto> {
    @Override
    public ExpenseCategory fromDto(CashFlowDto dto, String userId) {
        return new ExpenseCategory(userId, new CashFlowName(dto.name), new Money(dto.expectedAmount, dto.currency));
    }

    @Override
    public OutputCashFlowDto toDto(ExpenseCategory entity) {
        return OutputCashFlowDto.builder()
                .id(entity.getId())
                .name(entity.getName().getName())
                .currency(entity.getMonthlyExpense().getCurrency())
                .expectedAmount(entity.getMonthlyExpense().getAmount())
                .userId(entity.getUserId())
                .build();
    }
}
