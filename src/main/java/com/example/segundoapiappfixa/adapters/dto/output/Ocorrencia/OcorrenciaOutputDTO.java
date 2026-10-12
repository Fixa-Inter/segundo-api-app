package com.example.segundoapiappfixa.adapters.dto.output.Ocorrencia;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.util.List;
import com.example.segundoapiappfixa.adapters.dto.output.Foto.FotoOutputDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OcorrenciaOutputDTO(
        Long id,
        String titulo,
        String descricaoOcorrencia,
        String categoriaProblema,
        String prioridade,
        String localEndereco,
        String descricaoLocal,
        LocalDate dataCriacao,
        String categoriaEquipamento,
        String nomeUsuario,
        String tipoAcesso,
        List<FotoOutputDTO> fotos
) {
}
