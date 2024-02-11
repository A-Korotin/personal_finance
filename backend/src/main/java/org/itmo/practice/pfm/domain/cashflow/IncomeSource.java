package org.itmo.practice.pfm.domain.cashflow;

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
public class IncomeSource {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userId;

    @Embedded
    private CashFlowName name;

    @Embedded
    private Money monthlyIncome;

    public IncomeSource(String userId, CashFlowName name, Money monthlyIncome) {
        this.userId = Objects.requireNonNull(userId);
        this.name = Objects.requireNonNull(name);
        this.monthlyIncome = Objects.requireNonNull(monthlyIncome);
    }

    public IncomeSource(UUID id, String userId, CashFlowName name, Money monthlyIncome) {
        this(userId, name, monthlyIncome);
        this.id = Objects.requireNonNull(id);
    }
}
