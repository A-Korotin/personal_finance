package org.itmo.practice.pfm.domain.transaction;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.itmo.practice.pfm.domain.funds.Money;
import org.itmo.practice.pfm.domain.transaction.exception.TransactionExecutionException;
import org.itmo.practice.pfm.domain.transaction.exception.TransactionReversionException;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
public abstract class Transaction {
    @Id
    @GeneratedValue
    protected UUID id;

    protected String userId;

    @Enumerated(EnumType.STRING)
    protected TransactionType type;

    @Enumerated(EnumType.STRING)
    protected TransactionStatus status;

    @Embedded
    protected TransactionComment comment;

    protected ZonedDateTime timestamp;

    @Embedded
    protected Money money;

    public Transaction(String userId, TransactionType type, TransactionComment comment, Money money) {
        this.userId = Objects.requireNonNull(userId);
        this.type = Objects.requireNonNull(type);
        this.comment = Objects.requireNonNull(comment);
        this.money = Objects.requireNonNull(money);
        this.status = TransactionStatus.DRAFT;
    }

    public void execute(ZonedDateTime at) {
        if (status != TransactionStatus.DRAFT && status != TransactionStatus.CANCELLED) {
            throw new TransactionExecutionException("Transaction can not be executed");
        }
        this.timestamp = at;
        executeInternal(at);
    }

    public void revert() {
        if (status != TransactionStatus.EXECUTED) {
            throw new TransactionReversionException("Transaction can not be reverted");
        }
        revertInternal();
    }

    protected abstract void executeInternal(ZonedDateTime at);
    protected abstract void revertInternal();
}
