package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.ModeloEquipamentoMapper;
import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;
import com.example.segundoapiappfixa.domain.repository.ModeloEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.ModeloEquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaModeloEquipamentoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ModeloEquipamentoRepositoryImpl implements ModeloEquipamentoRepository {
    // Dependências
    private final JpaModeloEquipamentoRepository repository;
    private final ModeloEquipamentoMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    // Método de listar os registros persistidos no banco de dados
    public List<ModeloEquipamento> findByEnderecoId(Long enderecoId) {
        return repository
                .findAllByUsuario_Endereco_Id(enderecoId)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    public Optional<ModeloEquipamento> findById(Long id) {
        return repository
                .findById(id)
                .map(mapper::toModel);
    }

    // Método de salvar no banco de dados
    public ModeloEquipamento save(ModeloEquipamento model) {
        ModeloEquipamentoEntity entity = mapper.toEntity(model);

        entity.usuario = entityManager.getReference(
                UsuarioEntity.class,
                model.getUsuario().getId()
        );

        return mapper.toModel(repository.save(entity));
    }

    // Método de deletar dados persistidos no banco de dados
    public Optional<ModeloEquipamento> deleteById(Long id) {
        Optional<ModeloEquipamentoEntity> entity = repository.findById(id);

        if (entity.isEmpty()) return Optional.empty();

        repository.deleteById(id);
        return entity.map(mapper::toModel);
    }
}
