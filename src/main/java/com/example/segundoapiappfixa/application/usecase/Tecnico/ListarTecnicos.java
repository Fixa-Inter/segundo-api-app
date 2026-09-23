package com.example.segundoapiappfixa.application.usecase.Tecnico;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarTecnicos {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listar(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        List<Usuario> usuarios = usuarioRepository.findByEnderecoIdAndTipoAcesso(
                usuario.getEndereco().getId(),
                TipoAcesso.TECNICO
        );

        if (usuarios.isEmpty()) throw new EntidadeNaoEncontradaException("exception.usuario.required");

        return usuarios;
    }

}
