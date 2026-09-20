package com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OcorrenciaCadastrarInputDTO (
        @NotNull(message = "validation.usuario.required")
        Long usuarioId,

        @NotNull(message = "validation.local.required")
        Long localEnderecoId,

        @NotNull(message = "validation.equipamento.required")
        Long equipamentoId,

        @NotNull(message = "validation.categoria.required")
        Long categoriaProblemaId,

        @NotBlank(message = "validation.titulo.required")
        String titulo,

        @NotBlank(message = "validation.descricao.required")
        String descricaoOcorrencia,

        @NotBlank(message = "validation.descricaoLocal.required")
        String descricaoLocal,

        @NotNull(message = "validation.prioridade.required")
        Long prioridade
) {
}
