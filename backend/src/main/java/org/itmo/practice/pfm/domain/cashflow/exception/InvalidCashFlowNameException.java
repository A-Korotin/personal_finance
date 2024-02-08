package org.itmo.practice.pfm.domain.cashflow.exception;

public class InvalidCashFlowNameException extends RuntimeException{
    public InvalidCashFlowNameException(String message) {
        super(message);
    }
}
