package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosEquipamentoQueryParam;
import com.example.segundoapiappfixa.adapters.mapper.EquipamentoMapper;
import com.example.segundoapiappfixa.domain.model.Equipamento;
import com.example.segundoapiappfixa.domain.repository.EquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.*;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.repository.specs.EquipamentoSpecs;
import com.example.segundoapiappfixa.infrastructure.database.repository.utils.SortUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public List<Equipamento> findByModeloEquipamentoId(
            Long modeloEquipamentoId,
            FiltrosEquipamentoQueryParam filtros
    ) {

        Specification<EquipamentoEntity> specification = Specification.where(EquipamentoSpecs.findByModeloEquipamentoId(modeloEquipamentoId))
                .and(EquipamentoSpecs.findByCodigo(filtros.codigo()))
                .and(EquipamentoSpecs.findByLocalEndereco(filtros.localEndereco()))
                .and(EquipamentoSpecs.findByUsuario(filtros.usuario()));

        Sort sort = SortUtils.definirSort(
                SortUtils.normalizarCampoOrdenacao(filtros.campoOrdenacao(), camposNormalizados),
                filtros.direcaoOrdenacao()
        );

        Pageable pageable = filtros.limite() == null ? null :
                PageRequest.of(0, filtros.limite(), sort);

        List<EquipamentoEntity> equipamentos =
                pageable != null
                        ? repository.findAll(specification, pageable).getContent()
                        : repository.findAll(specification, sort);

        return equipamentos
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
