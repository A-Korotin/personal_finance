package org.itmo.practice.pfm.domain.cashflow;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.itmo.practice.pfm.domain.cashflow.exception.InvalidCashFlowNameException;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CashFlowName {
    private String name;

    public CashFlowName(String name) {
        name = Objects.requireNonNull(name);
        if (name.isBlank() || name.isEmpty()) {
            throw new InvalidCashFlowNameException("Cash flow name should not be empty");
        }
        name = name.strip();
        if (name.length() < 3 || name.length() > 100) {
            throw new InvalidCashFlowNameException("Cash flow name should be between 3 and 100 symbols");
        }
        this.name = name;
    }
}
