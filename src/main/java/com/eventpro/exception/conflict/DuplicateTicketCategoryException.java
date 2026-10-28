package com.eventpro.exception.conflict;

public class DuplicateTicketCategoryException extends ConflictException {
    public DuplicateTicketCategoryException(String message) {
        super(message);
    }

    public DuplicateTicketCategoryException() {
        this("This category is already attached to the event");
    }
}
