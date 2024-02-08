package org.itmo.practice.pfm.domain.funds;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.itmo.practice.pfm.domain.funds.exception.InvalidAmountException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class Money {
    private int amount;
    private Currency currency;

    public Money(int amount, Currency currency) {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount should be a positive number");
        }
        this.amount = amount;
        this.currency = currency;
    }
}
