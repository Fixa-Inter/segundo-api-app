package com.example.segundoapiappfixa.application.usecase.Notificacao;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Notificacao;
import com.example.segundoapiappfixa.domain.repository.NotificacaoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class MarcarNotificacaoVisualizada {
    private final NotificacaoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public Notificacao marcar(Long usuarioId, String notificacaoId) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        Notificacao notificacao = repository.findById(notificacaoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.notificacao.notFound"));

        if (!usuarioId.equals(notificacao.getUsuarioId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        notificacao.setVisualizada(true);
        return repository.save(notificacao);
    }
}
