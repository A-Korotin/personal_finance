package org.itmo.practice.pfm.domain.account.exception;

public class InvalidAccountNameException extends RuntimeException {
    public InvalidAccountNameException(String message) {
        super(message);
    }
}
