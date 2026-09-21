package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.CategoriaEquipamentoMapper;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.CategoriaEquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaCategoriaEquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class CategoriaEquipamentoRepositoryImpl implements CategoriaEquipamentoRepository {

    private final JpaCategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final CategoriaEquipamentoMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CategoriaEquipamento save(CategoriaEquipamento categoriaEquipamento) {
        CategoriaEquipamentoEntity entity = mapper.toEntity(categoriaEquipamento);

        entity.usuario = entityManager.getReference(
                UsuarioEntity.class,
                categoriaEquipamento.getUsuario().getId()
        );

        return mapper.toModel(categoriaEquipamentoRepository.save(entity));
    }

    public Optional<CategoriaEquipamento> findById(Long id) {
        return categoriaEquipamentoRepository
                .findById(id)
                .map(mapper::toModel);
    }

    @Override
    public Optional<CategoriaEquipamento> deleteById(Long id) {
        CategoriaEquipamentoEntity entity = categoriaEquipamentoRepository
                .findById(id)
                .orElse(null);

        if (entity == null) return Optional.empty();

        categoriaEquipamentoRepository.deleteById(id);
        return Optional.of(mapper.toModel(entity));
    }

    public List<CategoriaEquipamento> findByEnderecoId(Long usuarioEnderecoId) {
        List<CategoriaEquipamentoEntity> categoriasEquipamentos = categoriaEquipamentoRepository
                .findAllByUsuario_Endereco_Id(usuarioEnderecoId);

        return categoriasEquipamentos
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
