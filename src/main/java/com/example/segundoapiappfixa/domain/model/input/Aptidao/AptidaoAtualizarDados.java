package com.example.segundoapiappfixa.domain.model.input.Aptidao;

import java.math.BigDecimal;

public record AptidaoAtualizarDados(
        Long id,
        BigDecimal nota,
        Boolean estaAtivo
) {}
