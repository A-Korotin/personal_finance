package org.itmo.practice.pfm.presentation.http.account.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.itmo.practice.pfm.application.account.service.AccountService;
import org.itmo.practice.pfm.domain.account.AccountBalance;
import org.itmo.practice.pfm.domain.account.AccountName;
import org.itmo.practice.pfm.domain.account.UserAccount;
import org.itmo.practice.pfm.presentation.http.account.dto.AccountDto;
import org.itmo.practice.pfm.presentation.http.account.dto.OutputAccountDto;
import org.itmo.practice.pfm.presentation.http.account.mapper.AccountMapper;
import org.itmo.practice.pfm.presentation.http.controller.CrudRestController;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@Tag(name = "User account", description = "Entity representing fund storage")
@Validated
public class AccountRestController extends CrudRestController<UUID, UserAccount, AccountDto, OutputAccountDto> {

    private final AccountService service;

    public AccountRestController(AccountService accountService, AccountMapper accountMapper) {
        super(accountMapper, accountService);
        service = accountService;
    }

    @GetMapping
    @PageableAsQueryParam
    @Operation(summary = "Find all",
            description = "Find all accounts owned by user",
            responses = {@ApiResponse(description = "OK", responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(description = "Not authorized", responseCode = "401", content = {@Content(schema = @Schema(implementation = Void.class))})})
    public ResponseEntity<Page<OutputAccountDto>> findAll(Principal principal,
                                                          Pageable pageable) {
        return super.findAllByUserId(principal.getName(), pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account",
            description = "Get account by ID",
            responses = {@ApiResponse(description = "OK", responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(description = "Not authorized", responseCode = "401", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Not found", responseCode = "404", content = {@Content(schema = @Schema(implementation = Void.class))})})
    @PreAuthorize("@accountSecurity.userHasAccessToView(#id, authentication.name)")
    public ResponseEntity<OutputAccountDto> findById(@PathVariable UUID id) {

        return super.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create account",
            responses = {@ApiResponse(description = "Created", responseCode = "201", useReturnTypeSchema = true),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(description = "Not authorized", responseCode = "401", content = {@Content(schema = @Schema(implementation = Void.class))})})
    public ResponseEntity<OutputAccountDto> createAccount(@Valid @RequestBody AccountDto dto,
                                                          Principal principal) {
        return super.create(dto, principal.getName());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit account",
            description = "Edit account by ID",
            responses = {@ApiResponse(description = "OK", responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(description = "Not authorized", responseCode = "401", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Not found", responseCode = "404", content = {@Content(schema = @Schema(implementation = Void.class))})})
    @PreAuthorize("@accountSecurity.userHasAccessToModify(#id, authentication.name)")
    public ResponseEntity<OutputAccountDto> editAccountById(@PathVariable UUID id,
                                                            @Valid @RequestBody AccountDto dto,
                                                            Principal principal) {
        return this.update(id, dto, principal.getName());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account",
            description = "Delete account by ID",
            responses = {@ApiResponse(description = "OK", responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(description = "Not authorized", responseCode = "401", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Not found", responseCode = "404", content = {@Content(schema = @Schema(implementation = Void.class))})})
    @PreAuthorize("@accountSecurity.userHasAccessToModify(#id, authentication.name)")
    public ResponseEntity<Void> deleteAccountById(@PathVariable UUID id) {
        return super.delete(id);
    }


    @Override
    protected ResponseEntity<OutputAccountDto> update(UUID id, AccountDto dto, String userId) {
        if (!service.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserAccount newAccount = new UserAccount(id, userId,
                new AccountBalance(dto.amount, dto.currency),
                new AccountName(dto.name));

        newAccount = service.save(newAccount);

        return ResponseEntity.ok(mapper.toDto(newAccount));
    }
}
