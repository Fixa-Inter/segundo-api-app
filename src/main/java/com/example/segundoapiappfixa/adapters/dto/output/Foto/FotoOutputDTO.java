package com.example.segundoapiappfixa.adapters.dto.output.Foto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FotoOutputDTO(
        Long id,
        String url
) {
}
