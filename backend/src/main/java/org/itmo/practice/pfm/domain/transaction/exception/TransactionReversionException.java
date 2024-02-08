package org.itmo.practice.pfm.domain.transaction.exception;

public class TransactionReversionException extends RuntimeException {
    public TransactionReversionException(String message) {
        super(message);
    }
}
