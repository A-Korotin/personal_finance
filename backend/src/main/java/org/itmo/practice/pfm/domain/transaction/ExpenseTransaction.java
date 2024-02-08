package org.itmo.practice.pfm.domain.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.itmo.practice.pfm.domain.funds.Money;
import org.itmo.practice.pfm.domain.account.UserAccount;
import org.itmo.practice.pfm.domain.cashflow.ExpenseCategory;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpenseTransaction extends Transaction {

    @ManyToOne
    private ExpenseCategory category;

    @ManyToOne
    private UserAccount source;

    public ExpenseTransaction(String userId,TransactionComment comment, Money money,
                              ExpenseCategory category, UserAccount source) {
        super(userId, TransactionType.EXPENSE, comment, money);
        this.category = Objects.requireNonNull(category);
        this.source = Objects.requireNonNull(source);
    }

    @Override
    protected void executeInternal(ZonedDateTime at) {
        source.withdraw(this.money);
        this.status = TransactionStatus.EXECUTED;
    }

    @Override
    protected void revertInternal() {
        source.refill(this.money);
        this.status = TransactionStatus.CANCELLED;
    }
}
