package com.example.segundoapiappfixa.adapters.dto.output.Usuario;

import java.time.LocalDate;

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
