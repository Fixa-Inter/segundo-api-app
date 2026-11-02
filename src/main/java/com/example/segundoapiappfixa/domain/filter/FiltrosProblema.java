package com.example.segundoapiappfixa.domain.filter;

import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import java.time.LocalDateTime;

public record FiltrosProblema(
        String titulo,
        String descricaoProblema,
        String categoriaEquipamento,
        String localUnidade,
        String descricaoLocal,
        String equipamentoCodigo,
        String nomeUsuario,
        String tipoAcesso,
        StatusProblema statusProblema,
        LocalDateTime dataCriacaoMin,
        LocalDateTime dataCriacaoMax,
        Integer limite,
        String campoOrdenacao,
        Integer direcaoOrdenacao
) {}
