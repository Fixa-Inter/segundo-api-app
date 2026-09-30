package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.RecuperarSenhaCodigoMapper;
import com.example.segundoapiappfixa.domain.model.RecuperarSenhaCodigo;
import com.example.segundoapiappfixa.infrastructure.database.entity.RecuperarSenhaCodigoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaRecuperarSenhaCodigoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class RecuperarSenhaCodigoRepositoryImpl implements com.example.segundoapiappfixa.domain.repository.PasswordTokenRepository {

    // Dependências
    private final JpaRecuperarSenhaCodigoRepository repository;
    private final RecuperarSenhaCodigoMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    // Método de salvar no banco de dados
    @Override
    public void save(RecuperarSenhaCodigo recuperarSenhaCodigo) {
        RecuperarSenhaCodigoEntity entity = mapper.toEntity(recuperarSenhaCodigo);

        entity.usuario = entityManager.getReference(
                UsuarioEntity.class,
                recuperarSenhaCodigo.getUsuario().getId()
        );

        repository.save(entity);
    }

    // Método de encontrar último código criado pelo usuário
    @Override
    @Transactional(readOnly = true)
    public RecuperarSenhaCodigo findActiveByUsuarioId(Long usuarioId) {
        RecuperarSenhaCodigoEntity entity = repository
                .findFirstByUsuarioIdAndEstaAtivoTrueOrderByIdDesc(usuarioId);

        return entity == null ? null : mapper.toModel(entity);
    }
}
