package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosCategoriaEquipamentoQueryParam;
import com.example.segundoapiappfixa.adapters.mapper.CategoriaEquipamentoMapper;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.CategoriaEquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UsuarioEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaCategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs.CategoriaEquipamentoSpecs;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.utils.SortUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
                .findOne(Specification.where(CategoriaEquipamentoSpecs.findByEstaAtivo())
                        .and((root, query, cb) -> cb.equal(root.get("id"), id)))
                .map(mapper::toModel);
    }

    // Método de deletar dados persistidos no banco de dados
    @Override
    public Optional<CategoriaEquipamento> deleteById(Long id) {
        CategoriaEquipamentoEntity entity = repository
                .findById(id)
                .orElse(null);

        if (entity == null) return Optional.empty();

        entity.setEstaAtivo(false);
        repository.save(entity);
        return Optional.of(mapper.toModel(entity));
    }

    // Método de listar os registros persistidos no banco de dados
    public List<CategoriaEquipamento> findByEnderecoId(Long usuarioEnderecoId, FiltrosCategoriaEquipamentoQueryParam filtros) {

        Specification<CategoriaEquipamentoEntity> specification = Specification.where(CategoriaEquipamentoSpecs.findByEstaAtivo())
                .and(CategoriaEquipamentoSpecs.findByEnderecoId(usuarioEnderecoId))
                .and(CategoriaEquipamentoSpecs.findByNome(filtros.nome()))
                .and(CategoriaEquipamentoSpecs.findByDescricao(filtros.descricao()))
                .and(CategoriaEquipamentoSpecs.findByNomeUsuario(filtros.nomeUsuario()));

        Sort sort = SortUtils.definirSort(
                SortUtils.normalizarCampoOrdenacao(filtros.campoOrdenacao(), camposNormalizados),
                filtros.direcaoOrdenacao()
        );

        Pageable pageable = filtros.limite() == null ? null :
                PageRequest.of(0, filtros.limite(), sort);

        List<CategoriaEquipamentoEntity> categoriasEquipamentos =
                pageable != null
                ? repository.findAll(specification, pageable).getContent()
                : repository.findAll(specification, sort);

        return categoriasEquipamentos
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
