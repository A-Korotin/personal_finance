package org.itmo.practice.pfm.domain.account;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.itmo.practice.pfm.domain.account.exception.CurrencyMismatchException;
import org.itmo.practice.pfm.domain.funds.Currency;
import org.itmo.practice.pfm.domain.funds.Money;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class AccountBalance {
    private int balance;
    private Currency currency;

    public AccountBalance(int balance, Currency currency) {
        this.balance = balance;
        this.currency = currency;
    }

    public AccountBalance refill(Money money) {
        if (this.currency != money.getCurrency()) {
            throw new CurrencyMismatchException("Can not perform operations between %s and %s"
                    .formatted(this.currency, money.getCurrency()));
        }

        return new AccountBalance(this.balance + money.getAmount(), this.currency);
    }

    public AccountBalance withdraw(Money money) {
        if (this.currency != money.getCurrency()) {
            throw new CurrencyMismatchException("Can not perform operations between %s and %s"
                    .formatted(this.currency, money.getCurrency()));
        }

        return new AccountBalance(this.balance - money.getAmount(), this.currency);
    }
}
