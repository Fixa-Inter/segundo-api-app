package com.example.segundoapiappfixa.adapters.dto.output.CategoriaEquipamento;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoriaEquipamentoOutputDTO(
        Long id,
        String nome,
        String descricao,
        String nomeUsuario,
        LocalDate dataCriacao,
        Boolean estaAtivo
) {
}
