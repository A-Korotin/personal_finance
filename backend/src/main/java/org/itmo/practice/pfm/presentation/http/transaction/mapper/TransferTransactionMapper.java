package org.itmo.practice.pfm.presentation.http.transaction.mapper;

import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.account.service.AccountService;
import org.itmo.practice.pfm.domain.account.UserAccount;
import org.itmo.practice.pfm.domain.funds.Money;
import org.itmo.practice.pfm.domain.transaction.TransactionComment;
import org.itmo.practice.pfm.domain.transaction.TransferTransaction;
import org.itmo.practice.pfm.presentation.http.mapper.EntityMapper;
import org.itmo.practice.pfm.presentation.http.transaction.dto.OutputIncomeTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.dto.OutputTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.dto.OutputTransferTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.dto.TransferTransactionDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class TransferTransactionMapper
        implements EntityMapper<TransferTransaction, TransferTransactionDto, OutputTransferTransactionDto> {
    private final AccountService accountService;

    @Override
    public TransferTransaction fromDto(TransferTransactionDto dto, String userId) {
        UserAccount from = accountService.findById(dto.fromAccountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserAccount to = accountService.findById(dto.toAccountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new TransferTransaction(userId, new TransactionComment(dto.comment),
                new Money(dto.amount, dto.currency), from, to);
    }

    @Override
    public OutputTransferTransactionDto toDto(TransferTransaction entity) {
        OutputTransferTransactionDto dto = OutputTransferTransactionDto.builder()
                .fromAccountId(entity.getSource().getId())
                .toAccountId(entity.getDestination().getId()).build();

        dto.id = entity.getId();
        dto.status = entity.getStatus();
        dto.type = entity.getType();
        dto.timestamp = entity.getTimestamp();
        dto.comment = entity.getComment().getComment();
        dto.amount = entity.getMoney().getAmount();
        dto.currency = entity.getMoney().getCurrency();
        dto.userId = entity.getUserId();

        return dto;
    }
}
