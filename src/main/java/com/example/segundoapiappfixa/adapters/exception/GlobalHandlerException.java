package com.example.segundoapiappfixa.adapters.exception;

import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

import java.util.NoSuchElementException;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {

    private final MessageSource messages;

    public GlobalHandlerException(MessageSource messages) {
        this.messages = messages;
    }

    private String text(String key) {
        return messages.getMessage(key, null, key, LocaleContextHolder.getLocale());
    }

    private ResponseEntity<?> response(HttpStatus status, String key) {
        return ResponseEntity.status(status).body(Map.of("message", text(key)));
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    ResponseEntity<?> notFound(EntidadeNaoEncontradaException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", text(e.getMessage())));
    }

    @ExceptionHandler(RegraProblemaException.class)
    ResponseEntity<?> rule(RegraProblemaException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of("message", text(e.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> validation(MethodArgumentNotValidException e) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("message", e.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).toList()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<?> unreadable(HttpMessageNotReadableException e) {
        return response(HttpStatus.BAD_REQUEST, "exception.request.invalid");
    }

    @ExceptionHandler({ConstraintViolationException.class, ConversionFailedException.class,
            IllegalArgumentException.class})
    ResponseEntity<?> invalidInput(Exception e) {
        return response(HttpStatus.BAD_REQUEST, "exception.request.invalid");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<?> integrity(DataIntegrityViolationException e) {
        return response(HttpStatus.CONFLICT, "exception.database.integrity");
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    ResponseEntity<?> optimisticLock(OptimisticLockingFailureException e) {
        return response(HttpStatus.CONFLICT, "exception.database.concurrent");
    }

    @ExceptionHandler({CannotAcquireLockException.class, PessimisticLockingFailureException.class})
    ResponseEntity<?> databaseLock(Exception e) {
        return response(HttpStatus.LOCKED, "exception.database.lock");
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    ResponseEntity<?> invalidDataAccess(InvalidDataAccessApiUsageException e) {
        return response(HttpStatus.BAD_REQUEST, "exception.database.invalidUsage");
    }

    @ExceptionHandler({JpaSystemException.class, TransactionSystemException.class})
    ResponseEntity<?> persistence(Exception e) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class,
            EmptyResultDataAccessException.class})
    ResponseEntity<?> notFound(Exception e) {
        return response(HttpStatus.NOT_FOUND, "exception.entity.notFound");
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> generic(Exception e) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
