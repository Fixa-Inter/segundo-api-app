package com.example.segundoapiappfixa.domain.model.input.Problema;

import com.example.segundoapiappfixa.domain.enums.StatusProblema;

public record ProblemaAtualizarStatusDados(
        Long problemaId,
        StatusProblema statusProblema,
        String motivoRecusa
) {}
