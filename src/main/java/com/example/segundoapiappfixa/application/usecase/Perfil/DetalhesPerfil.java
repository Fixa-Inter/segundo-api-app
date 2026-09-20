package com.example.segundoapiappfixa.application.usecase.Perfil;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class DetalhesPerfil {

    private final UsuarioRepository usuarioRepository;

    public Usuario detalhesPerfil(Long usuarioId) {

        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (usuario.isEmpty()) {
            throw new EntidadeNaoEncontradaException("validation.usuario.required");
        }

        return usuario.get();
    }
}
