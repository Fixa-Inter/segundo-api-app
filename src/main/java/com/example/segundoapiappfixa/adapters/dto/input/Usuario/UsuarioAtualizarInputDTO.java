package com.example.segundoapiappfixa.adapters.dto.input.Usuario;

import com.example.segundoapiappfixa.domain.model.input.Usuario.UsuarioAtualizarDados;

public record UsuarioAtualizarInputDTO (
        String nomeCompleto,
        String cargo,
        String urlFotoPerfil
) {
    public UsuarioAtualizarDados toDomain() {
        return new UsuarioAtualizarDados(nomeCompleto, cargo, urlFotoPerfil);
    }
}
