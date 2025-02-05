package com.telecentro.api.infra.exception;

import com.telecentro.api.domain.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UniqueException.class)
    protected ResponseEntity<ExceptionDTO> handleUniqueException(UniqueException ex) {
        log.warn("Attribute must be unique");
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.CONFLICT
                )
        );
    }

    @ExceptionHandler(CredentialsException.class)
    protected ResponseEntity<ExceptionDTO> handleCredentialsException(CredentialsException ex) {
        log.warn("Invalid credentials");
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.UNAUTHORIZED
                )
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ExceptionDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.warn("Entity not found");
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND
                )
        );
    }

    @ExceptionHandler(SameTimeException.class)
    protected ResponseEntity<ExceptionDTO> handleSameTimeException(SameTimeException ex) {
        log.warn("Course already exists at same time");
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.CONFLICT
                )
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ExceptionDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.warn("Data integrity violation");
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<List<DataException>> handleDataRequestException(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors()
                .stream()
                .map(DataException::new)
                .toList();
        log.warn("Data request exception");

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionDTO> handleException(Exception ex) {
        log.error("Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
        );
    }

}
