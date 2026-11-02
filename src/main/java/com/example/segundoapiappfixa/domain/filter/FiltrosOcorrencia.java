package com.example.segundoapiappfixa.domain.filter;

import java.time.LocalDateTime;

public record FiltrosOcorrencia(
        String titulo,
        String descricaoOcorrencia,
        String categoriaProblema,
        String prioridade,
        String localUnidade,
        String descricaoLocal,
        String categoriaEquipamento,
        String nomeUsuario,
        String tipoAcesso,
        LocalDateTime dataCriacaoMin,
        LocalDateTime dataCriacaoMax,
        Integer limite,
        String campoOrdenacao,
        Integer direcaoOrdenacao
) {}
