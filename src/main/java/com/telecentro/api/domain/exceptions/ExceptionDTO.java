package com.telecentro.api.domain.exceptions;

import org.springframework.http.HttpStatus;

public record ExceptionDTO(
        String message,
        HttpStatus status
) {
}
