package com.telecentro.api.domain.exceptions;

public class UniqueException extends RuntimeException {
    public UniqueException(String message) {
        super(message);
    }
}
