package com.eventpro.exception.tickets;

import com.eventpro.exception.BaseException;

public class TicketAlreadyUsedException extends BaseException {
    public TicketAlreadyUsedException(String message) {
        super(message);
    }
    public TicketAlreadyUsedException() {
        this("This ticket has been used already");
    }
}
