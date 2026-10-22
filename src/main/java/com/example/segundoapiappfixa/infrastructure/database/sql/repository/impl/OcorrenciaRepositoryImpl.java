package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.OcorrenciaMapper;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosOcorrenciaQueryParam;
import com.example.segundoapiappfixa.domain.model.ObservacaoConclusao;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.*;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs.OcorrenciaSpecs;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.utils.SortUtils;
import com.example.segundoapiappfixa.domain.model.Ocorrencia;
import com.example.segundoapiappfixa.domain.repository.OcorrenciaRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaOcorrenciaRepository;
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
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Types;

@Repository
@Transactional
@RequiredArgsConstructor
public class OcorrenciaRepositoryImpl implements OcorrenciaRepository {

    // Dependências
    private final JpaOcorrenciaRepository jpaRepository;
    private final OcorrenciaMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    private List<Ocorrencia> buscar(Specification<OcorrenciaEntity> base, FiltrosOcorrenciaQueryParam filtros) {
        Specification<OcorrenciaEntity> specification = OcorrenciaSpecs.findByEstaAtivo()
                .and(base)
                .and(OcorrenciaSpecs.findByTitulo(filtros.titulo()))
                .and(OcorrenciaSpecs.findByDescricaoOcorrencia(filtros.descricaoOcorrencia()))
                .and(OcorrenciaSpecs.findByCategoriaProblema(filtros.categoriaProblema()))
                .and(OcorrenciaSpecs.findByPrioridade(filtros.prioridade()))
                .and(OcorrenciaSpecs.findByLocalUnidade(filtros.localUnidade()))
                .and(OcorrenciaSpecs.findByDescricaoLocal(filtros.descricaoLocal()))
                .and(OcorrenciaSpecs.findByCategoriaEquipamento(filtros.categoriaEquipamento()))
                .and(OcorrenciaSpecs.findByNomeUsuario(filtros.nomeUsuario()))
                .and(OcorrenciaSpecs.findByTipoAcesso(filtros.tipoAcesso()))
                .and(OcorrenciaSpecs.findByDataCriacaoMin(filtros.dataCriacaoMin()))
                .and(OcorrenciaSpecs.findByDataCriacaoMax(filtros.dataCriacaoMax()));

        Sort sort = SortUtils.definirSort(
                SortUtils.normalizarCampoOrdenacao(filtros.campoOrdenacao(), camposNormalizados),
                filtros.direcaoOrdenacao()
        );

        Pageable pageable = filtros.limite() == null ? null :
                PageRequest.of(0, filtros.limite(), sort);

        List<OcorrenciaEntity> entidades = pageable != null
                ? jpaRepository.findAll(specification, pageable).getContent()
                : jpaRepository.findAll(specification, sort);

        return entidades
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Ocorrencia> findAllByUsuarioId(Long usuarioId, FiltrosOcorrenciaQueryParam filtros) {
        return buscar(OcorrenciaSpecs.findByUsuarioId(usuarioId), filtros);
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Ocorrencia> findAllByLocalUnidadeIds(List<Long> localUnidadeIds, FiltrosOcorrenciaQueryParam filtros) {
        if (localUnidadeIds == null || localUnidadeIds.isEmpty()) {
            return List.of();
        }

        return buscar(OcorrenciaSpecs.findByLocalUnidadeIds(localUnidadeIds), filtros);
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<Ocorrencia> findById(Long id) {
        return jpaRepository
                .findOne(Specification.where(OcorrenciaSpecs.findByEstaAtivo())
                        .and((root, query, cb) -> cb.equal(root.get("id"), id)))
                .map(mapper::toModel);
    }

    @Override
    public Long save(Ocorrencia ocorrencia, List<String> fotos) {
        return entityManager
                .unwrap(org.hibernate.Session.class)
                .doReturningWork(connection -> {
                    Array urls = connection.createArrayOf("varchar", fotos.toArray(String[]::new));

                    try (CallableStatement statement = connection.prepareCall(
                            "CALL cadastrar_ocorrencia(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
                    ) {
                        statement.setLong(1, ocorrencia.getUsuario().getId());
                        statement.setLong(2, ocorrencia.getLocalUnidade().getId());
                        statement.setLong(3, ocorrencia.getCategoriaEquipamento().getId());
                        statement.setInt(4, ocorrencia.getCategoriaProblema().getId());
                        statement.setInt(5, ocorrencia.getPrioridade().getId());
                        statement.setString(6, ocorrencia.getTitulo());
                        statement.setString(7, ocorrencia.getDescricaoOcorrencia());
                        statement.setString(8, ocorrencia.getDescricaoLocal());
                        statement.registerOutParameter(9, Types.BIGINT);
                        statement.setArray(10, urls);
                        statement.execute();

                        return statement.getLong(9);

                    } finally {
                        urls.free();
                    }
                });
    }

    public Ocorrencia update(Ocorrencia ocorrencia) {
        OcorrenciaEntity entity = mapper.toEntity(ocorrencia);
        return mapper.toModel(jpaRepository.save(entity));
    }

    // Método de deletar dados persistidos no banco de dados
    @Override
    public Optional<Ocorrencia> deleteById(Long id) {
        OcorrenciaEntity entity = jpaRepository.findById(id).orElse(null);
        if (entity == null) return Optional.empty();

        entity.setEstaAtivo(false);
        jpaRepository.save(entity);
        return Optional.of(mapper.toModel(entity));
    }

    private OcorrenciaEntity toEntityWithReferences(Ocorrencia ocorrencia) {
        OcorrenciaEntity entity = mapper.toEntity(ocorrencia);

        entity.usuario = reference(UsuarioEntity.class, ocorrencia.getUsuario().getId());

        entity.localUnidade = reference(LocalUnidadeEntity.class, ocorrencia.getLocalUnidade().getId());

        if (ocorrencia.getCategoriaEquipamento() != null) {
            entity.categoriaEquipamento = reference(CategoriaEquipamentoEntity.class, ocorrencia.getCategoriaEquipamento().getId());
        }

        return entity;
    }

    private <T> T reference(Class<T> type, Long id) {
        return entityManager.getReference(type, id);
    }
}
