package org.itmo.practice.pfm.presentation.http.cashflow.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.itmo.practice.pfm.application.cashflow.service.IncomeSourceService;
import org.itmo.practice.pfm.domain.cashflow.CashFlowName;
import org.itmo.practice.pfm.domain.cashflow.IncomeSource;
import org.itmo.practice.pfm.domain.funds.Money;
import org.itmo.practice.pfm.presentation.http.cashflow.dto.CashFlowDto;
import org.itmo.practice.pfm.presentation.http.cashflow.dto.OutputCashFlowDto;
import org.itmo.practice.pfm.presentation.http.cashflow.mapper.IncomeSourceMapper;
import org.itmo.practice.pfm.presentation.http.controller.CrudRestController;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/income-sources")
@Tag(name = "Income source", description = "Entity representing one of the user's income sources")
@Validated
public class IncomeSourceRestController
        extends CrudRestController<UUID, IncomeSource, CashFlowDto, OutputCashFlowDto> {
    private final IncomeSourceService service;

    public IncomeSourceRestController(IncomeSourceMapper mapper, IncomeSourceService service) {
        super(mapper, service);
        this.service = service;
    }

    @GetMapping
    @PageableAsQueryParam
    public ResponseEntity<Page<OutputCashFlowDto>> findAll(Principal principal,
                                                           Pageable pageable) {
        return super.findAllByUserId(principal.getName(), pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@incomeSecurity.userHasAccessToView(#id, authentication.name)")
    public ResponseEntity<OutputCashFlowDto> findById(@PathVariable UUID id) {
        return super.findById(id);
    }

    @PostMapping
    public ResponseEntity<OutputCashFlowDto> createCategory(@Valid @RequestBody CashFlowDto dto,
                                                            Principal principal) {
        return super.create(dto, principal.getName());
    }

    @PutMapping("/{id}")
    @PreAuthorize("@incomeSecurity.userHasAccessToModify(#id, authentication.name)")
    public ResponseEntity<OutputCashFlowDto> updateCategory(@Valid @RequestBody CashFlowDto dto,
                                                            @PathVariable UUID id,
                                                            Principal principal) {
        return this.update(id, dto, principal.getName());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@incomeSecurity.userHasAccessToModify(#id, authentication.name)")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        return super.delete(id);
    }

    @Override
    protected ResponseEntity<OutputCashFlowDto> update(UUID id, CashFlowDto dto, String userId) {
        if (!service.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        IncomeSource source = new IncomeSource(id, userId,
                new CashFlowName(dto.name), new Money(dto.expectedAmount, dto.currency));

        source = service.save(source);

        return ResponseEntity.ok(mapper.toDto(source));
    }
}
