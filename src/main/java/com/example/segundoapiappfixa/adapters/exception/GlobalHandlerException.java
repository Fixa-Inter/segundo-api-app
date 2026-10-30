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
import org.springframework.validation.BindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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
        if (key == null || key.isBlank()) {
            return messages.getMessage(
                    "exception.request.invalid",
                    null,
                    "Não foi possível processar a requisição.",
                    LocaleContextHolder.getLocale()
            );
        }
        return messages.getMessage(key, null, key, LocaleContextHolder.getLocale());
    }

    private String validationText(String message) {
        return text(message);
    }

    private ResponseEntity<?> response(HttpStatus status, String key) {
        return ResponseEntity.status(status).body(Map.of("message", text(key)));
    }

    private void printError(Exception e) {
        System.out.println("[API ERROR] " + e.getClass().getName() + ": " + e.getMessage());
        e.printStackTrace(System.out);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    ResponseEntity<?> notFound(EntidadeNaoEncontradaException e) {
        printError(e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", text(e.getMessage())));
    }

    @ExceptionHandler(RegraProblemaException.class)
    ResponseEntity<?> rule(RegraProblemaException e) {
        printError(e);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of("message", text(e.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> validation(MethodArgumentNotValidException e) {
        printError(e);
        return ResponseEntity
                .badRequest()
                .body(Map.of("message", e.getBindingResult().getFieldErrors().stream()
                        .map(x -> validationText(x.getDefaultMessage()))
                        .toList()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<?> unreadable(HttpMessageNotReadableException e) {
        printError(e);
        return response(HttpStatus.BAD_REQUEST, "exception.request.invalid");
    }

    @ExceptionHandler({BindException.class, MethodArgumentTypeMismatchException.class})
    ResponseEntity<?> invalidParameter(Exception e) {
        printError(e);
        return response(HttpStatus.BAD_REQUEST, "exception.parameter.invalid");
    }

    @ExceptionHandler({ConstraintViolationException.class, ConversionFailedException.class,
            IllegalArgumentException.class})
    ResponseEntity<?> invalidInput(Exception e) {
        printError(e);
        return response(HttpStatus.BAD_REQUEST, "exception.request.invalid");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<?> integrity(DataIntegrityViolationException e) {
        printError(e);
        return response(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    ResponseEntity<?> optimisticLock(OptimisticLockingFailureException e) {
        printError(e);
        return response(HttpStatus.CONFLICT, "exception.database.concurrent");
    }

    @ExceptionHandler({CannotAcquireLockException.class, PessimisticLockingFailureException.class})
    ResponseEntity<?> databaseLock(Exception e) {
        printError(e);
        return response(HttpStatus.LOCKED, "exception.database.lock");
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    ResponseEntity<?> invalidDataAccess(InvalidDataAccessApiUsageException e) {
        printError(e);
        return response(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({JpaSystemException.class, TransactionSystemException.class})
    ResponseEntity<?> persistence(Exception e) {
        printError(e);
        return response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class,
            EmptyResultDataAccessException.class})
    ResponseEntity<?> notFound(Exception e) {
        printError(e);
        return response(HttpStatus.NOT_FOUND, "exception.entity.notFound");
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> generic(Exception e) {
        printError(e);
        return response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
