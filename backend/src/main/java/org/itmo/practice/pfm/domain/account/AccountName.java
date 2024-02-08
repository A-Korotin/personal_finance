package org.itmo.practice.pfm.domain.account;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.itmo.practice.pfm.domain.account.exception.InvalidAccountNameException;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AccountName {
    private String name;

    public AccountName(String name) {
        name = Objects.requireNonNull(name);
        if (name.isBlank() || name.isEmpty()) {
            throw new InvalidAccountNameException("Account name should not be empty");
        }
        name = name.strip();
        if (name.length() < 3 || name.length() > 100) {
            throw new InvalidAccountNameException("Account name should be between 3 and 100 symbols");
        }

        this.name = name;
    }
}
