package org.itmo.practice.pfm.domain.transaction;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class TransactionComment {
    private String comment;

    public TransactionComment(String comment) {
        if (comment == null) {
            this.comment = "";
            return;
        }

        comment = comment.strip();
        if (comment.length() >= 100) {
            throw new RuntimeException(); //todo throw domain exception
        }
        this.comment = comment;
    }
}
