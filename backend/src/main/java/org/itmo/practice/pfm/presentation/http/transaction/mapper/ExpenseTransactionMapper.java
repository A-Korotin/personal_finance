package org.itmo.practice.pfm.presentation.http.transaction.mapper;

import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.account.service.AccountService;
import org.itmo.practice.pfm.application.cashflow.service.ExpenseCategoryService;
import org.itmo.practice.pfm.domain.account.UserAccount;
import org.itmo.practice.pfm.domain.cashflow.ExpenseCategory;
import org.itmo.practice.pfm.domain.funds.Money;
import org.itmo.practice.pfm.domain.transaction.ExpenseTransaction;
import org.itmo.practice.pfm.domain.transaction.TransactionComment;
import org.itmo.practice.pfm.presentation.http.mapper.EntityMapper;
import org.itmo.practice.pfm.presentation.http.transaction.dto.ExpenseTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.dto.OutputExpenseTransactionDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class ExpenseTransactionMapper
        implements EntityMapper<ExpenseTransaction, ExpenseTransactionDto, OutputExpenseTransactionDto> {

    private final ExpenseCategoryService expenseCategoryService;
    private final AccountService accountService;

    @Override
    public ExpenseTransaction fromDto(ExpenseTransactionDto dto, String userId) {
        ExpenseCategory category = expenseCategoryService.findById(dto.categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        UserAccount account = accountService.findById(dto.accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ExpenseTransaction(userId, new TransactionComment(dto.comment),
                new Money(dto.amount, dto.currency), category, account);
    }

    @Override
    public OutputExpenseTransactionDto toDto(ExpenseTransaction entity) {
        OutputExpenseTransactionDto dto = OutputExpenseTransactionDto.builder()
                .accountId(entity.getSource().getId())
                .categoryId(entity.getCategory().getId()).build();

        dto.id = entity.getId();
        dto.type = entity.getType();
        dto.comment = entity.getComment().getComment();
        dto.status = entity.getStatus();
        dto.amount = entity.getMoney().getAmount();
        dto.currency = entity.getMoney().getCurrency();
        dto.timestamp = entity.getTimestamp();

        return dto;
    }
}
