package org.itmo.practice.pfm.presentation.http.transaction.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.itmo.practice.pfm.domain.funds.Currency;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class IncomeTransactionDto {
    public String comment;
    @NotNull
    public ZonedDateTime timestamp;
    @NotNull
    public Currency currency;
    @NotNull
    public Integer amount;
    @NotNull
    public UUID sourceId;
    @NotNull
    public UUID accountId;

}
