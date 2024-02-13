package org.itmo.practice.pfm.presentation.http.transaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.transaction.service.TransactionService;
import org.itmo.practice.pfm.domain.transaction.ExpenseTransaction;
import org.itmo.practice.pfm.domain.transaction.Transaction;
import org.itmo.practice.pfm.presentation.http.transaction.dto.ExpenseTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.dto.OutputExpenseTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.mapper.ExpenseTransactionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Tag(name="Transactions")
@RequestMapping("/transactions/expense")
@RequiredArgsConstructor
@Validated
public class ExpenseTransactionRestController {
    private final TransactionService service;
    private final ExpenseTransactionMapper mapper;

    @PostMapping
    @Operation(summary = "Create expense transaction",
            responses = {@ApiResponse(description = "Created", responseCode = "201", useReturnTypeSchema = true),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(description = "Not authorized", responseCode = "401", content = {@Content(schema = @Schema(implementation = Void.class))})})
    public ResponseEntity<OutputExpenseTransactionDto> createExpense(
            Principal principal,
            @Valid @RequestBody ExpenseTransactionDto dto) {
        ExpenseTransaction transaction = mapper.fromDto(dto, principal.getName());
        transaction.execute(dto.timestamp);
        transaction = (ExpenseTransaction) service.save(transaction);
        return new ResponseEntity<>(mapper.toDto(transaction), HttpStatus.CREATED);
    }
}
