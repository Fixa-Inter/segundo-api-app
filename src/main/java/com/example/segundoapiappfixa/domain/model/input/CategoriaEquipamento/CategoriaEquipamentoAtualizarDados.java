package com.example.segundoapiappfixa.domain.model.input.CategoriaEquipamento;



public record CategoriaEquipamentoAtualizarDados(
        Long id,
        String nome,
        String descricao,
        Boolean estaAtivo
) {}
