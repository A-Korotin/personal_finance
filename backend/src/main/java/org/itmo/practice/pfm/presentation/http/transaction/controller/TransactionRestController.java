package org.itmo.practice.pfm.presentation.http.transaction.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.transaction.service.TransactionService;
import org.itmo.practice.pfm.presentation.http.transaction.dto.ExpenseTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.dto.OutputExpenseTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.dto.OutputTransactionDto;
import org.itmo.practice.pfm.presentation.http.transaction.mapper.TransactionMapper;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@Tag(name = "Transactions")
@RequestMapping("/transactions")
@Validated
@RequiredArgsConstructor
public class TransactionRestController {
    private final TransactionService service;
    private final TransactionMapper mapper;

    @GetMapping
    @PageableAsQueryParam
    public ResponseEntity<Page<OutputTransactionDto>> findAllTransactions(Principal principal, Pageable pageable) {
        return ResponseEntity.ok(service.findAllByUserId(principal.getName(), pageable).map(mapper::toDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@transactionSecurity.userHasAccessToView(#id, authentication.name)")
    public ResponseEntity<OutputTransactionDto> findById(@PathVariable UUID id) {
        return ResponseEntity.of(service.findById(id).map(mapper::toDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@transactionSecurity.userHasAccessToModify(#id, authentication.name)")
    public ResponseEntity<Void> revertById(@PathVariable UUID id) {
        try {
            service.revert(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
