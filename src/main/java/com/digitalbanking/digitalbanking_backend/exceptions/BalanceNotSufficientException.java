package com.digitalbanking.digitalbanking_backend.exceptions;

public class BalanceNotSufficientException extends Exception {
    public BalanceNotSufficientException(String balance_not_sufficient) {
        super(balance_not_sufficient);
    }
}
