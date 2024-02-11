package org.itmo.practice.pfm.domain.transaction;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.itmo.practice.pfm.domain.transaction.exception.TransactionCommentException;

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
            throw new TransactionCommentException("Transaction comment should be between 0 and 100 characters");
        }
        this.comment = comment;
    }
}
