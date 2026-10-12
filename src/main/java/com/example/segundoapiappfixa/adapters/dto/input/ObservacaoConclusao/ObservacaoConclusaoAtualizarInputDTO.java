package com.example.segundoapiappfixa.adapters.dto.input.ObservacaoConclusao;

import com.example.segundoapiappfixa.domain.enums.Dificuldade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record ObservacaoConclusaoAtualizarInputDTO(
        @NotNull(message = "{validation.observacaoConclusao.id.required}")
        Long id,

        Dificuldade dificuldade,
        String observacao,
        List<@NotBlank(message = "{validation.observacaoConclusao.foto.required}") String> fotos
) {}
