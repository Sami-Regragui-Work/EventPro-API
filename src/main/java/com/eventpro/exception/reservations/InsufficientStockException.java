package com.eventpro.exception.reservations;

import com.eventpro.exception.BaseException;

public class InsufficientStockException extends BaseException {
    public InsufficientStockException(String message) {
        super(message);
    }
    public InsufficientStockException() {
        this("Event slots are full");
    }
}
