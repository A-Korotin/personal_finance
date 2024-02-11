package org.itmo.practice.pfm.domain.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.itmo.practice.pfm.domain.funds.Money;
import org.itmo.practice.pfm.domain.account.UserAccount;
import org.itmo.practice.pfm.domain.transaction.exception.TransactionExecutionException;

import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TransferTransaction extends Transaction {

    @ManyToOne
    private UserAccount source;

    @ManyToOne
    private UserAccount destination;

    public TransferTransaction(String userId, TransactionComment comment, Money money,
                               UserAccount source, UserAccount destination) {
        super(userId, TransactionType.TRANSFER, comment, money);
        this.source = source;
        this.destination = destination;
    }

    @Override
    protected void executeInternal(ZonedDateTime at) {
        if (source.equals(destination)) {
            throw new TransactionExecutionException("Source can not be equal to the destination");
        }
        source.withdraw(this.money);
        destination.refill(this.money);
        this.status = TransactionStatus.EXECUTED;
    }

    @Override
    protected void revertInternal() {
        source.refill(this.money);
        destination.withdraw(this.money);
        this.status = TransactionStatus.CANCELLED;
    }
}
