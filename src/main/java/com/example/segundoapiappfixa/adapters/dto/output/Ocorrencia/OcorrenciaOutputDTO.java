package com.example.segundoapiappfixa.adapters.dto.output.Ocorrencia;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;

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
        String equipamentoCodigo,
        String nomeUsuario,
        String tipoAcesso
) {
}
