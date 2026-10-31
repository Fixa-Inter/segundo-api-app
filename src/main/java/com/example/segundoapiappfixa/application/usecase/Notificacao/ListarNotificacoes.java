package com.example.segundoapiappfixa.application.usecase.Notificacao;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Notificacao;
import com.example.segundoapiappfixa.domain.repository.NotificacaoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarNotificacoes {

    private final NotificacaoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public List<Notificacao> listar(Long usuarioId) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        return repository.findAllByUsuarioId(usuarioId);
    }
}
