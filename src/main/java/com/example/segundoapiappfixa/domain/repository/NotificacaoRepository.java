package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Notificacao;

import java.util.List;
import java.util.Optional;

public interface NotificacaoRepository {
    Notificacao save(Notificacao notificacao);

    List<Notificacao> findAllByUsuarioId(Long usuarioId);

    Optional<Notificacao> findById(String notificacaoId);

    void deleteById(String notificacaoId);
}
