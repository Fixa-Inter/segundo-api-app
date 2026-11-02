package com.example.segundoapiappfixa.domain.filter;



public record FiltrosUsuario(
        String nomeCompleto,
        String email,
        String cargo,
        String tipoAcesso,
        String cnpjUnidade,
        String dataNascimento,
        Boolean estaAtivo,
        Integer limite,
        String campoOrdenacao,
        Integer direcaoOrdenacao
) {}
