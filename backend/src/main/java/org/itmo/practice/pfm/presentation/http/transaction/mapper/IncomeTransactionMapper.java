package org.itmo.practice.pfm.presentation.http.transaction.mapper;

import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.account.service.AccountService;
import org.itmo.practice.pfm.application.cashflow.service.IncomeSourceService;
import org.itmo.practice.pfm.domain.account.UserAccount;
import org.itmo.practice.pfm.domain.cashflow.IncomeSource;
import org.itmo.practice.pfm.domain.funds.Money;
import org.itmo.practice.pfm.domain.transaction.IncomeTransaction;
import org.itmo.practice.pfm.domain.transaction.TransactionComment;
import org.itmo.practice.pfm.presentation.http.mapper.EntityMapper;
import org.itmo.practice.pfm.presentation.http.transaction.dto.IncomeTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.dto.OutputIncomeTransactionDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class IncomeTransactionMapper
        implements EntityMapper<IncomeTransaction, IncomeTransactionDto, OutputIncomeTransactionDto> {
    private final IncomeSourceService incomeSourceService;
    private final AccountService accountService;

    @Override
    public IncomeTransaction fromDto(IncomeTransactionDto dto, String userId) {
        IncomeSource source = incomeSourceService.findById(dto.sourceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserAccount account = accountService.findById(dto.accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new IncomeTransaction(userId,
                new TransactionComment(dto.comment), new Money(dto.amount, dto.currency), source, account);
    }

    @Override
    public OutputIncomeTransactionDto toDto(IncomeTransaction entity) {
        OutputIncomeTransactionDto dto = OutputIncomeTransactionDto.builder()
                .accountId(entity.getDestination().getId())
                .sourceId(entity.getSource().getId())
                .build();

        dto.id = entity.getId();
        dto.type = entity.getType();
        dto.userId = entity.getUserId();
        dto.comment = entity.getComment().getComment();
        dto.status = entity.getStatus();
        dto.amount = entity.getMoney().getAmount();
        dto.currency = entity.getMoney().getCurrency();
        dto.timestamp = entity.getTimestamp();

        return dto;
    }
}
