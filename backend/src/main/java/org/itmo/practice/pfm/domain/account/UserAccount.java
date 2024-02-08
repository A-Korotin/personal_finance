package org.itmo.practice.pfm.domain.account;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.itmo.practice.pfm.domain.funds.Money;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    protected String userId;

    @Embedded
    protected AccountBalance balance;

    @Embedded
    protected AccountName name;

    public UserAccount(String userId, AccountBalance balance, AccountName name) {
        this.userId = Objects.requireNonNull(userId);
        this.balance = Objects.requireNonNull(balance);
        this.name = Objects.requireNonNull(name);
    }

    public void withdraw(Money money) {
        balance = balance.withdraw(money);
    }

    public void refill(Money money) {
        balance = balance.refill(money);
    }

    public void rename(AccountName newName) {
        name = Objects.requireNonNull(newName);
    }
}
