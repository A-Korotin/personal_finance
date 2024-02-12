package org.itmo.practice.pfm.presentation.http.transaction.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.transaction.service.TransactionService;
import org.itmo.practice.pfm.domain.transaction.IncomeTransaction;
import org.itmo.practice.pfm.domain.transaction.TransferTransaction;
import org.itmo.practice.pfm.presentation.http.transaction.dto.IncomeTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.dto.OutputIncomeTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.dto.OutputTransferTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.dto.TransferTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.mapper.IncomeTransactionMapper;
import org.itmo.practice.pfm.presentation.http.transaction.mapper.TransferTransactionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Tag(name = "Transactions")
@RequestMapping("/transactions/transfer")
@RequiredArgsConstructor
@Validated
public class TransferTransactionRestController {
    private final TransactionService service;
    private final TransferTransactionMapper mapper;

    @PostMapping
    public ResponseEntity<OutputTransferTransactionDto> createTransfer(
            Principal principal,
            @Valid @RequestBody TransferTransactionDto dto) {
        TransferTransaction transaction = mapper.fromDto(dto, principal.getName());
        transaction.execute(dto.timestamp);
        transaction = (TransferTransaction)  service.save(transaction);
        return new ResponseEntity<>(mapper.toDto(transaction), HttpStatus.CREATED);
    }
}
