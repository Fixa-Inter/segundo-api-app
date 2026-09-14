package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import com.example.segundoapiappfixa.adapters.mapper.UsuarioMapper;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final JpaUsuarioRepository repository;
    private final UsuarioMapper mapper;

    @Override
    public Usuario findByEmail(String email) {
        UsuarioEntity entity = repository.findUsuarioByEmail(email);
        return mapper.toModel(entity);
    }

    @Override
    public Usuario findById(Long id) {
        UsuarioEntity usuarioEntity = repository.findUsuarioEntitiesById(id);
        return mapper.toModel(usuarioEntity);
    }
}
