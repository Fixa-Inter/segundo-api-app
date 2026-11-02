package com.example.segundoapiappfixa.domain.model.auth;

import java.time.LocalDate;

public record CadastroDados(
        String nomeCompleto,
        String email,
        String senha,
        Long unidadeId,
        String cargo,
        LocalDate dataNascimento
) { }
