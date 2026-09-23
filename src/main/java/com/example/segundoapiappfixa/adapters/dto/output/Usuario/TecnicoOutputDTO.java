package com.example.segundoapiappfixa.adapters.dto.output.Usuario;

import com.example.segundoapiappfixa.adapters.dto.output.Turno.TurnoOutputDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TecnicoOutputDTO(
        Long id,
        String nomeCompleto,
        String email,
        String cargo,
        String tipoAcesso,
        LocalDate dataNascimento,
        LocalDate dataCriacao,
        Boolean estaAtivo,
        List<TurnoOutputDTO> turnos

) {
}
