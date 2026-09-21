package com.example.segundoapiappfixa.adapters.dto.output.Usuario;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsuarioOutputDTO (
        Long id,
        String nomeCompleto,
        String email,
        String tipoAcesso,
        String cargo,
        LocalDate dataNascimento,
        String cnpjEndereco
) {
}
