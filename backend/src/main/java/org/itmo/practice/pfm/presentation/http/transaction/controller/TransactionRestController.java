package org.itmo.practice.pfm.presentation.http.transaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.transaction.service.TransactionService;
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
    @Operation(summary = "Find all",
            description = "Find all transactions performed by user",
            responses = {@ApiResponse(description = "OK", responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(description = "Not authorized", responseCode = "401", content = {@Content(schema = @Schema(implementation = Void.class))})})
    @PageableAsQueryParam
    public ResponseEntity<Page<OutputTransactionDto>> findAllTransactions(Principal principal, Pageable pageable) {
        return ResponseEntity.ok(service.findAllByUserId(principal.getName(), pageable).map(mapper::toDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction",
            description = "Get transaction by ID",
            responses = {@ApiResponse(description = "OK", responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(description = "Not authorized", responseCode = "401", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Not found", responseCode = "404", content = {@Content(schema = @Schema(implementation = Void.class))})})
    @PreAuthorize("@transactionSecurity.userHasAccessToView(#id, authentication.name)")
    public ResponseEntity<OutputTransactionDto> findById(@PathVariable UUID id) {
        return ResponseEntity.of(service.findById(id).map(mapper::toDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel transaction",
            description = "Cancel transaction by ID",
            responses = {@ApiResponse(description = "OK", responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(description = "Not authorized", responseCode = "401", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Not found", responseCode = "404", content = {@Content(schema = @Schema(implementation = Void.class))})})
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
