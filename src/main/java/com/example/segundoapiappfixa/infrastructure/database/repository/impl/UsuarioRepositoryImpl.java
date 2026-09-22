package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import com.example.segundoapiappfixa.adapters.mapper.UsuarioMapper;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaUsuarioRepository;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {

    // Dependências
    private final JpaUsuarioRepository repository;
    private final UsuarioMapper mapper;

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<Usuario> findByEmail(String email) {
        UsuarioEntity entity = repository.findUsuarioByEmail(email);
        return Optional.ofNullable(entity).map(mapper::toModel);
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<Usuario> findById(Long id) {
        return repository
                .findById(id)
                .map(mapper::toModel);
    }
}
