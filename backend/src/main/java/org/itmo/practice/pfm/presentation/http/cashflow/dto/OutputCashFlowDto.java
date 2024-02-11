package org.itmo.practice.pfm.presentation.http.cashflow.dto;

import lombok.Builder;
import lombok.Data;
import org.itmo.practice.pfm.domain.funds.Currency;

import java.util.UUID;

@Data
@Builder
public class OutputCashFlowDto {
    public UUID id;
    public String userId;
    public String name;
    public Currency currency;
    public Integer expectedAmount;
}
