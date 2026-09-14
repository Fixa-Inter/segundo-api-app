package com.example.segundoapiappfixa.adapters.exception;

import com.example.segundoapiappfixa.infrastructure.exception.ProblemaNaoEncontradoException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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

    @ExceptionHandler(ProblemaNaoEncontradoException.class)
    ResponseEntity<?> notFound(ProblemaNaoEncontradoException e) {
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

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> generic(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Internal server error"));
    }
}
