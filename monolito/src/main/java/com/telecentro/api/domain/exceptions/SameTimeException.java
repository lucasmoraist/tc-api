package com.telecentro.api.domain.exceptions;

public class SameTimeException extends RuntimeException {
    public SameTimeException(String message) {
        super(message);
    }
}
