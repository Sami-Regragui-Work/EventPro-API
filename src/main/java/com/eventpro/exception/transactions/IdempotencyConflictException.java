package com.eventpro.exception.transactions;

import com.eventpro.exception.BaseException;

public class IdempotencyConflictException extends BaseException {
    public IdempotencyConflictException(String message) {
        super(message);
    }
    public IdempotencyConflictException() {
        this("Repeated Idempotency for different cart");
    }
}
