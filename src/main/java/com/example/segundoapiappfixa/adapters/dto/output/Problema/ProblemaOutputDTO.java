package com.example.segundoapiappfixa.adapters.dto.output.Problema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.example.segundoapiappfixa.adapters.dto.output.Foto.FotoOutputDTO;
import java.util.List;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProblemaOutputDTO (
        Long id,
        String titulo,
        String descricaoProblema,
        String categoriaEquipamento,
        String localEndereco,
        String descricaoLocal,
        String status,
        String usuario,
        String tipoAcesso,
        LocalDate dataCriacao,
        List<FotoOutputDTO> fotos
) {
}
