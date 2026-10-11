package com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OcorrenciaCadastrarInputDTO (
        @NotNull(message = "{validation.usuario.required}")
        Long usuarioId,

        @NotNull(message = "{validation.local.required}")
        Long localEnderecoId,

        @NotNull(message = "{validation.categoriaEquipamento.required}")
        Long categoriaEquipamentoId,

        @NotNull(message = "{validation.categoria.required}")
        CategoriaProblema categoriaProblemaId,

        @NotBlank(message = "{validation.titulo.required}")
        String titulo,

        @NotBlank(message = "{validation.descricao.required}")
        String descricaoOcorrencia,

        @NotBlank(message = "{validation.descricaoLocal.required}")
        String descricaoLocal,

        @NotNull(message = "{validation.prioridade.required}")
        Prioridade prioridade
) {
}
