package com.example.segundoapiappfixa.domain.model.auth;

public record IdentidadeGoogle(
        String email,
        String nome,
        boolean emailVerificado
) { }
