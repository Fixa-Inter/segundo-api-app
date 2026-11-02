package com.example.segundoapiappfixa.domain.model.input.MarcaEquipamento;



public record MarcaEquipamentoAtualizarDados(
        Long id,
        String nome,
        String descricao,
        Boolean estaAtivo
) {}
