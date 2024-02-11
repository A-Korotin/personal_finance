package org.itmo.practice.pfm.presentation.http.account.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.itmo.practice.pfm.domain.funds.Currency;

@Data
public class AccountDto {
    @NotNull
    public String name;
    @NotNull
    public Currency currency;
    @NotNull
    public Integer amount;
}
