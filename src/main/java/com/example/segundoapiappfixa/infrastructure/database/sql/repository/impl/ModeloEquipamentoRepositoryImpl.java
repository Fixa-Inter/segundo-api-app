package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.ModeloEquipamentoMapper;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosModeloEquipamentoQueryParam;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs.ModeloEquipamentoSpecs;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.utils.SortUtils;
import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;
import com.example.segundoapiappfixa.domain.repository.ModeloEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ModeloEquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UsuarioEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaModeloEquipamentoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class ModeloEquipamentoRepositoryImpl implements ModeloEquipamentoRepository {
    // Dependências
    private final JpaModeloEquipamentoRepository repository;
    private final ModeloEquipamentoMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    // Método de listar os registros persistidos no banco de dados
    public List<ModeloEquipamento> findByEnderecoId(Long enderecoId, FiltrosModeloEquipamentoQueryParam filtros) {
        Specification<ModeloEquipamentoEntity> specification = Specification.where(ModeloEquipamentoSpecs.findByEstaAtivo())
                .and(ModeloEquipamentoSpecs.findByEnderecoId(enderecoId))
                .and(ModeloEquipamentoSpecs.findByNome(filtros.nome()))
                .and(ModeloEquipamentoSpecs.findByDescricao(filtros.descricao()))
                .and(ModeloEquipamentoSpecs.findByMarca(filtros.marca()))
                .and(ModeloEquipamentoSpecs.findByCategoria(filtros.categoria()));

        Sort sort = SortUtils.definirSort(
                SortUtils.normalizarCampoOrdenacao(filtros.campoOrdenacao(), camposNormalizados),
                filtros.direcaoOrdenacao()
        );

        Pageable pageable = filtros.limite() == null ? null :
                PageRequest.of(0, filtros.limite(), sort);

        List<ModeloEquipamentoEntity> modelos = pageable != null
                ? repository.findAll(specification, pageable).getContent()
                : repository.findAll(specification, sort);

        return modelos
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    public Optional<ModeloEquipamento> findById(Long id) {
        return repository
                .findOne(Specification.where(ModeloEquipamentoSpecs.findByEstaAtivo())
                        .and((root, query, cb) -> cb.equal(root.get("id"), id)))
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

        entity.get().setEstaAtivo(false);
        repository.save(entity.get());
        return entity.map(mapper::toModel);
    }
}
