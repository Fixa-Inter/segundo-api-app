package com.example.segundoapiappfixa.infrastructure.database.redis.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.NotificacaoMapper;
import com.example.segundoapiappfixa.domain.model.Notificacao;
import com.example.segundoapiappfixa.domain.repository.NotificacaoRepository;
import com.example.segundoapiappfixa.infrastructure.database.redis.entity.NotificacaoEntity;
import com.example.segundoapiappfixa.infrastructure.database.redis.repository.JpaNotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificacaoRedisRepository implements NotificacaoRepository {
    private static final int MAX_PER_USER = 200;

    private final JpaNotificacaoRepository repository;
    private final NotificacaoMapper mapper;

    @Override
    public Notificacao save(Notificacao notificacao) {
        NotificacaoEntity saved = repository.save(mapper.toEntity(notificacao));
        limitarHistorico(notificacao.getUsuarioId());
        return mapper.toModel(saved);
    }

    @Override
    public List<Notificacao> findAllByUsuarioId(Long usuarioId) {
        return repository.findAllByUsuarioId(usuarioId).stream()
                .map(mapper::toModel)
                .sorted(Comparator.comparing(Notificacao::getDataCriacao).reversed())
                .toList();
    }

    @Override
    public Optional<Notificacao> findById(String notificacaoId) {
        return repository.findById(notificacaoId)
                .map(mapper::toModel);
    }

    @Override
    public void deleteById(String notificacaoId) {
        repository.deleteById(notificacaoId);
    }

    private void limitarHistorico(Long usuarioId) {
        List<NotificacaoEntity> notificacoes = repository.findAllByUsuarioId(usuarioId);

        notificacoes.stream()
                .sorted(Comparator.comparing(NotificacaoEntity::getDataCriacao).reversed())
                .skip(MAX_PER_USER)
                .forEach(repository::delete);
    }
}
