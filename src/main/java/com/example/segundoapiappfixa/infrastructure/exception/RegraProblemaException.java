package com.example.segundoapiappfixa.infrastructure.exception;
public class RegraProblemaException extends RuntimeException {
    public RegraProblemaException(String messageKey) { super(messageKey); }
}
