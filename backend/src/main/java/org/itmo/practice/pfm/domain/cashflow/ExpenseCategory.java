package org.itmo.practice.pfm.domain.cashflow;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.itmo.practice.pfm.domain.funds.Money;

import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
public class ExpenseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    private CashFlowName name;

    @Embedded
    private Money monthlyExpense;

    public ExpenseCategory(CashFlowName name, Money monthlyExpense) {
        this.name = Objects.requireNonNull(name);
        this.monthlyExpense = Objects.requireNonNull(monthlyExpense);
    }
}
