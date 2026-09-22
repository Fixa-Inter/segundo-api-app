package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.EventoMapper;
import com.example.segundoapiappfixa.domain.model.Evento;
import com.example.segundoapiappfixa.domain.repository.EventoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.EventoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.LocalEnderecoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaEventoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EventoRepositoryImpl implements EventoRepository {

    // Dependências
    private final JpaEventoRepository repository;
    private final EventoMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Evento> findByUsuarioId(Long usuarioId) {
        return repository.findAllByUsuario_Id(usuarioId).stream().map(mapper::toModel).toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<Evento> findById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    // Método de salvar no banco de dados
    @Override
    public Evento save(Evento evento) {
        EventoEntity entity = mapper.toEntity(evento);

        entity.usuario = entityManager.getReference(
                UsuarioEntity.class,
                evento.getUsuario().getId()
        );

        entity.localEndereco = entityManager.getReference(
                LocalEnderecoEntity.class,
                evento.getLocalEndereco().getId()
        );

        return mapper.toModel(repository.save(entity));
    }

    // Método de deletar dados persistidos no banco de dados
    @Override
    public Optional<Evento> deleteById(Long id) {
        EventoEntity entity = repository.findById(id).orElse(null);

        if (entity == null) return Optional.empty();

        repository.deleteById(id);
        return Optional.of(mapper.toModel(entity));
    }
}
