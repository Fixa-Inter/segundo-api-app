package com.example.segundoapiappfixa.infrastructure.database.redis.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.RecuperarSenhaCodigoMapper;
import com.example.segundoapiappfixa.domain.model.RecuperarSenhaCodigo;
import com.example.segundoapiappfixa.domain.repository.RecuperarSenhaCodigoRepository;
import com.example.segundoapiappfixa.infrastructure.database.redis.entity.RecuperarSenhaCodigoEntity;
import com.example.segundoapiappfixa.infrastructure.database.redis.repository.JpaRecuperarSenhaCodigoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecuperarSenhaCodigoRepositoryImpl implements RecuperarSenhaCodigoRepository {

    // Dependências
    private final JpaRecuperarSenhaCodigoRepository repository;
    private final RecuperarSenhaCodigoMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    // Método de salvar no banco de dados
    @Override
    public void save(RecuperarSenhaCodigo recuperarSenhaCodigo) {
        RecuperarSenhaCodigoEntity entity = mapper.toEntity(recuperarSenhaCodigo);
        repository.save(entity);
    }

    // Método de encontrar último código criado pelo usuário
    @Override
    @Transactional(readOnly = true)
    public RecuperarSenhaCodigo findByUsuarioIdAndEstaAtivo(Long usuarioId) {
        RecuperarSenhaCodigoEntity entity = repository
                .findByUsuarioIdAndEstaAtivo(usuarioId, true);

        return entity == null ? null : mapper.toModel(entity);
    }

    // Método de deletar o hash da última chave ativa
    public void deleteAll(Long usuarioId) {
        List<RecuperarSenhaCodigoEntity> recuperarSenhaCodigos = repository.findAllByUsuarioId(usuarioId);

        if (!recuperarSenhaCodigos.isEmpty()) {
            repository.deleteAll(recuperarSenhaCodigos);
        }
    }
}
