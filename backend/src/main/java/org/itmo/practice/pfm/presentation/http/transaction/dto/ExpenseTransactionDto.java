package org.itmo.practice.pfm.presentation.http.transaction.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.itmo.practice.pfm.domain.funds.Currency;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class ExpenseTransactionDto {
    public String comment;
    @NotNull
    public ZonedDateTime timestamp;
    @NotNull
    public Currency currency;
    @NotNull
    public Integer amount;
    @NotNull
    public UUID categoryId;
    @NotNull
    public UUID accountId;
}
