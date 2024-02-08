package org.itmo.practice.pfm.domain.account.exception;

public class CurrencyMismatchException extends RuntimeException{
    public CurrencyMismatchException(String message) {
        super(message);
    }
}
