package com.example.segundoapiappfixa.adapters.dto.input.ObservacaoConclusao;

import com.example.segundoapiappfixa.domain.enums.Dificuldade;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record ObservacaoConclusaoCriarInputDTO(
        @NotNull(message = "{validation.observacaoConclusao.ordemServico.required}")
        Long ordemServicoId,

        @NotNull(message = "{validation.observacaoConclusao.dificuldade.required}")
        Dificuldade dificuldade,

        String observacao,

        @NotNull(message = "{validation.observacaoConclusao.status.required}")
        Long statusOrdemServicoId,

        @NotEmpty(message = "{validation.observacaoConclusao.fotos.required}")
        List<@NotBlank(message = "{validation.observacaoConclusao.foto.required}") String> fotos
) {}
