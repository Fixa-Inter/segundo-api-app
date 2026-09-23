package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.EquipamentoMapper;
import com.example.segundoapiappfixa.domain.model.Equipamento;
import com.example.segundoapiappfixa.domain.repository.EquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.EquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.LocalEnderecoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.ModeloEquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class EquipamentoRepositoryImpl implements EquipamentoRepository {

    // Dependências
    private final JpaEquipamentoRepository repository;
    private final EquipamentoMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Equipamento> findByModeloEquipamentoId(Long modeloEquipamentoId) {
        return repository.findAllByModeloEquipamento_Id(modeloEquipamentoId)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    public Optional<Equipamento> findById(Long id) {
        return repository
                .findById(id)
                .map(mapper::toModel);
    }

    // Método de salvar no banco de dados
    @Override
    public Equipamento save(Equipamento equipamento) {
        EquipamentoEntity entity = mapper.toEntity(equipamento);

        entity.usuario = entityManager.getReference(
                UsuarioEntity.class,
                equipamento.getUsuario().getId()
        );

        entity.modeloEquipamento = entityManager.getReference(
                ModeloEquipamentoEntity.class,
                equipamento.getModeloEquipamento().getId()
        );

        entity.localEndereco = entityManager.getReference(
                LocalEnderecoEntity.class,
                equipamento.getLocalEndereco().getId()
        );

        return mapper.toModel(repository.save(entity));
    }

    // Método de deletar dados persistidos no banco de dados
    @Override
    public Optional<Equipamento> deleteById(Long id) {
        EquipamentoEntity entity = repository.findById(id).orElse(null);
        if (entity == null) return Optional.empty();

        repository.deleteById(id);
        return Optional.of(mapper.toModel(entity));
    }

}
