package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.CategoriaEquipamentoMapper;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.CategoriaEquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaCategoriaEquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
@RequiredArgsConstructor
public class CategoriaEquipamentoRepositoryImpl implements CategoriaEquipamentoRepository {

    // Dependências
    private final JpaCategoriaEquipamentoRepository repository;
    private final CategoriaEquipamentoMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    // Método de salvar no banco de dados
    @Override
    public CategoriaEquipamento save(CategoriaEquipamento categoriaEquipamento) {
        CategoriaEquipamentoEntity entity = mapper.toEntity(categoriaEquipamento);

        entity.usuario = entityManager.getReference(
                UsuarioEntity.class,
                categoriaEquipamento.getUsuario().getId()
        );

        return mapper.toModel(repository.save(entity));
    }

    // Método de listar os registros persistidos no banco de dados
    public Optional<CategoriaEquipamento> findById(Long id) {
        return repository
                .findById(id)
                .map(mapper::toModel);
    }

    // Método de deletar dados persistidos no banco de dados
    @Override
    public Optional<CategoriaEquipamento> deleteById(Long id) {
        CategoriaEquipamentoEntity entity = repository
                .findById(id)
                .orElse(null);

        if (entity == null) return Optional.empty();

        repository.deleteById(id);
        return Optional.of(mapper.toModel(entity));
    }

    // Método de listar os registros persistidos no banco de dados
    public List<CategoriaEquipamento> findByEnderecoId(Long usuarioEnderecoId) {
        List<CategoriaEquipamentoEntity> categoriasEquipamentos = repository
                .findAllByUsuario_Endereco_Id(usuarioEnderecoId);

        return categoriasEquipamentos
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
