package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import com.example.segundoapiappfixa.adapters.mapper.UsuarioMapper;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaUsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {

    // Dependências
    private final JpaUsuarioRepository repository;
    private final UsuarioMapper mapper;

    // Método de listar os registros persistidos no banco de dados
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(String email) {
        UsuarioEntity entity = repository.findUsuarioByEmail(email);
        return Optional.ofNullable(entity).map(mapper::toModel);
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return repository
                .findById(id)
                .map(mapper::toModel);
    }

    // Método de listar todos os usuários de um endereço e tipo de acesso específico persistidos no banco de dados
    @Transactional(readOnly = true)
    public List<Usuario> findByEnderecoIdAndTipoAcesso(
            Long enderecoId,
            TipoAcesso tipoAcesso
    ) {
        List<UsuarioEntity> usuarioEntities = repository.findUsuarioEntitiesByEndereco_IdAndTipoAcesso(
                enderecoId,
                tipoAcesso
        );

        return usuarioEntities
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
