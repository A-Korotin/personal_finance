package org.itmo.practice.pfm.presentation.http.cashflow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.itmo.practice.pfm.domain.funds.Currency;

@Data
public class CashFlowDto {
    @NotNull
    public String name;
    @NotNull
    public Currency currency;
    @NotNull
    public Integer expectedAmount;
}
