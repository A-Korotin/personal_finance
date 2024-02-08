package org.itmo.practice.pfm.domain.transaction.exception;

public class TransactionExecutionException extends RuntimeException {
    public TransactionExecutionException(String message) {
        super(message);
    }
}
