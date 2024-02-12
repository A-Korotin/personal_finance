package org.itmo.practice.pfm.presentation.http.transaction.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.transaction.service.TransactionService;
import org.itmo.practice.pfm.domain.transaction.IncomeTransaction;
import org.itmo.practice.pfm.presentation.http.transaction.dto.IncomeTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.dto.OutputIncomeTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.mapper.IncomeTransactionMapper;
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
@RequestMapping("/transactions/income")
@RequiredArgsConstructor
@Validated
public class IncomeTransactionRestController {
    private final TransactionService service;
    private final IncomeTransactionMapper mapper;

    @PostMapping
    public ResponseEntity<OutputIncomeTransactionDto> createIncome(
            Principal principal,
            @Valid @RequestBody IncomeTransactionDto dto) {
        IncomeTransaction transaction = mapper.fromDto(dto, principal.getName());
        transaction.execute(dto.timestamp);
        transaction = (IncomeTransaction) service.save(transaction);
        return new ResponseEntity<>(mapper.toDto(transaction), HttpStatus.CREATED);
    }
}
