package org.itmo.practice.pfm.domain.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.itmo.practice.pfm.domain.funds.Money;
import org.itmo.practice.pfm.domain.account.UserAccount;
import org.itmo.practice.pfm.domain.cashflow.IncomeSource;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IncomeTransaction extends Transaction {

    @ManyToOne
    private IncomeSource source;

    @ManyToOne
    private UserAccount destination;

    public IncomeTransaction(String userId, TransactionComment comment, Money money,
                             IncomeSource source, UserAccount destination) {
        super(userId, TransactionType.EXPENSE, comment, money);

        this.source = Objects.requireNonNull(source);
        this.destination = Objects.requireNonNull(destination);
    }

    @Override
    protected void executeInternal(ZonedDateTime at) {
        destination.refill(this.money);
        this.status = TransactionStatus.EXECUTED;
    }

    @Override
    protected void revertInternal() {
        destination.withdraw(this.money);
        this.status = TransactionStatus.CANCELLED;
    }
}
