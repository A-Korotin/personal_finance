package org.itmo.practice.pfm.presentation.http.transaction.mapper;

import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.domain.transaction.ExpenseTransaction;
import org.itmo.practice.pfm.domain.transaction.IncomeTransaction;
import org.itmo.practice.pfm.domain.transaction.Transaction;
import org.itmo.practice.pfm.domain.transaction.TransferTransaction;
import org.itmo.practice.pfm.presentation.http.transaction.dto.OutputTransactionDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    private final TransferTransactionMapper transferTransactionMapper;
    private final IncomeTransactionMapper incomeTransactionMapper;
    private final ExpenseTransactionMapper expenseTransactionMapper;

    // todo create generic type resolution
    public OutputTransactionDto toDto(Transaction entity) {
        return switch (entity.getType()) {
            case TRANSFER -> transferTransactionMapper.toDto((TransferTransaction) entity);
            case EXPENSE -> expenseTransactionMapper.toDto((ExpenseTransaction) entity);
            case INCOME -> incomeTransactionMapper.toDto((IncomeTransaction) entity);
        };
    }
}
