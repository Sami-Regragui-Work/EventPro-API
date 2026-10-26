package com.eventpro.exception.reservations;

import com.eventpro.exception.BaseException;

public class ReservationExpiredException extends BaseException {
    public ReservationExpiredException(String message) {
        super(message);
    }
    public ReservationExpiredException() {
        this("This reservation expired");
    }
}
