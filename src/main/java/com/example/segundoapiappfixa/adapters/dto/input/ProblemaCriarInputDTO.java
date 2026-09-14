package com.example.segundoapiappfixa.adapters.dto.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProblemaCriarInputDTO(
        @NotBlank(message = "{validation.titulo.required}")
        String titulo,

        @NotBlank(message = "{validation.descricao.required}")
        String descricaoProblema,

        @NotNull(message = "{validation.local.required}")
        Long localEnderecoID,

        @NotBlank(message = "{validation.descricaoLocal.required}")
        String descricaoLocal,

        @NotNull(message = "{validation.categoria.required}")
        Long categoriaEquipamentoId,

        @NotEmpty(message = "{validation.fotos.required}")
        List<@NotBlank(message = "{validation.foto.required}") String> urlsFoto
) {


}
