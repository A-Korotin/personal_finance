package org.itmo.practice.pfm.presentation.http.cashflow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.itmo.practice.pfm.application.cashflow.service.ExpenseCategoryService;
import org.itmo.practice.pfm.domain.cashflow.CashFlowName;
import org.itmo.practice.pfm.domain.cashflow.ExpenseCategory;
import org.itmo.practice.pfm.domain.funds.Money;
import org.itmo.practice.pfm.presentation.http.cashflow.dto.CashFlowDto;
import org.itmo.practice.pfm.presentation.http.cashflow.dto.OutputCashFlowDto;
import org.itmo.practice.pfm.presentation.http.cashflow.mapper.ExpenseCategoryMapper;
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
@RequestMapping("/expense-categories")
@Tag(name = "Expense category", description = "Entity representing one of the user's expense categories")
@Validated
public class ExpenseCategoryRestController
        extends CrudRestController<UUID, ExpenseCategory, CashFlowDto, OutputCashFlowDto> {
    private final ExpenseCategoryService service;

    public ExpenseCategoryRestController(ExpenseCategoryService service, ExpenseCategoryMapper mapper) {
        super(mapper, service);
        this.service = service;
    }

    @GetMapping
    @PageableAsQueryParam
    @Operation(summary = "Find all",
            description = "Find all expense categories owned by user",
            responses = {@ApiResponse(description = "OK", responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(description = "Not authorized", responseCode = "401", content = {@Content(schema = @Schema(implementation = Void.class))})})
    public ResponseEntity<Page<OutputCashFlowDto>> findAll(Principal principal,
                                                           Pageable pageable) {
        return super.findAllByUserId(principal.getName(), pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get expense category",
            description = "Get expense category by ID",
            responses = {@ApiResponse(description = "OK", responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(description = "Not authorized", responseCode = "401", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Not found", responseCode = "404", content = {@Content(schema = @Schema(implementation = Void.class))})})
    @PreAuthorize("@expenseSecurity.userHasAccessToView(#id, authentication.name)")
    public ResponseEntity<OutputCashFlowDto> findById(@PathVariable UUID id) {
        return super.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create expense category",
            responses = {@ApiResponse(description = "Created", responseCode = "201", useReturnTypeSchema = true),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(description = "Not authorized", responseCode = "401", content = {@Content(schema = @Schema(implementation = Void.class))})})
    public ResponseEntity<OutputCashFlowDto> createCategory(@Valid @RequestBody CashFlowDto dto,
                                                            Principal principal) {
        return super.create(dto, principal.getName());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit expense category",
            description = "Edit expense category by ID",
            responses = {@ApiResponse(description = "OK", responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = {@Content(schema = @Schema(implementation = String.class))}),
                    @ApiResponse(description = "Not authorized", responseCode = "401", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = {@Content(schema = @Schema(implementation = Void.class))}),
                    @ApiResponse(description = "Not found", responseCode = "404", content = {@Content(schema = @Schema(implementation = Void.class))})})
    @PreAuthorize("@expenseSecurity.userHasAccessToModify(#id, authentication.name)")
    public ResponseEntity<OutputCashFlowDto> updateCategory(@Valid @RequestBody CashFlowDto dto,
                                                            @PathVariable UUID id,
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
    @PreAuthorize("@expenseSecurity.userHasAccessToModify(#id, authentication.name)")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        return super.delete(id);
    }

    @Override
    protected ResponseEntity<OutputCashFlowDto> update(UUID id, CashFlowDto dto, String userId) {
        if (!service.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ExpenseCategory category = new ExpenseCategory(id, userId,
                new CashFlowName(dto.name), new Money(dto.expectedAmount, dto.currency));

        category = service.save(category);

        return ResponseEntity.ok(mapper.toDto(category));
    }
}
