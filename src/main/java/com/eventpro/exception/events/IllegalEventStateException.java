package com.eventpro.exception.events;

public class IllegalEventStateException extends RuntimeException {
    public IllegalEventStateException(String message) {
        super(message);
    }
    public IllegalEventStateException() {
        this("Cannot make this status transition");
    }
    public IllegalEventStateException(String currentStatus, String desiredStatus) {
        this("Cannot change event from %s to %s".formatted(currentStatus, desiredStatus));
    }
}
