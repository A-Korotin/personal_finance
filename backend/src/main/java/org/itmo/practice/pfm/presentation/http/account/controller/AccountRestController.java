package org.itmo.practice.pfm.presentation.http.account.controller;

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
    public ResponseEntity<Page<OutputAccountDto>> findAll(Principal principal,
                                                          Pageable pageable) {
        return super.findAllByUserId(principal.getName(), pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@accountSecurity.userHasAccessToView(#id, authentication.name)")
    public ResponseEntity<OutputAccountDto> findById(@PathVariable UUID id) {

        return super.findById(id);
    }

    @PostMapping
    public ResponseEntity<OutputAccountDto> createAccount(@Valid @RequestBody AccountDto dto,
                                                          Principal principal) {
        return super.create(dto, principal.getName());
    }

    @PutMapping("/{id}")
    @PreAuthorize("@accountSecurity.userHasAccessToModify(#id, authentication.name)")
    public ResponseEntity<OutputAccountDto> editAccountById(@PathVariable UUID id,
                                                            @Valid @RequestBody AccountDto dto,
                                                            Principal principal) {
        return this.update(id, dto, principal.getName());
    }

    @DeleteMapping("/{id}")
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
