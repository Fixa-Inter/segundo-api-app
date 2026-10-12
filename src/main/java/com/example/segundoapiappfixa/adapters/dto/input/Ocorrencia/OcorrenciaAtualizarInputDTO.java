package com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record OcorrenciaAtualizarInputDTO(
        @NotNull(message = "{validation.ocorrencia.required}")
        @Positive(message = "{validation.ocorrencia.invalid}")
        Long id,

        Long localEnderecoId,
        Long categoriaEquipamentoId,

        CategoriaProblema categoriaProblemaId,

        String titulo,
        String descricaoOcorrencia,
        String descricaoLocal,

        Prioridade prioridade,
        List<@NotBlank(message = "{validation.foto.required}") String> urlsFoto
) {
}
