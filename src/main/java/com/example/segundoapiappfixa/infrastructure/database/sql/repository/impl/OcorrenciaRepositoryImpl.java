package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.OcorrenciaMapper;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosOcorrenciaQueryParam;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs.OcorrenciaSpecs;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.utils.SortUtils;
import com.example.segundoapiappfixa.domain.model.Ocorrencia;
import com.example.segundoapiappfixa.domain.repository.OcorrenciaRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.EquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.LocalEnderecoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.OcorrenciaEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UsuarioEntity;
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
                .and(OcorrenciaSpecs.findByLocalEndereco(filtros.localEndereco()))
                .and(OcorrenciaSpecs.findByDescricaoLocal(filtros.descricaoLocal()))
                .and(OcorrenciaSpecs.findByEquipamentoCodigo(filtros.equipamentoCodigo()))
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
    public List<Ocorrencia> findAllByLocalEnderecoIds(List<Long> localEnderecoIds, FiltrosOcorrenciaQueryParam filtros) {
        if (localEnderecoIds == null || localEnderecoIds.isEmpty()) {
            return List.of();
        }

        return buscar(OcorrenciaSpecs.findByLocalEnderecoIds(localEnderecoIds), filtros);
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<Ocorrencia> findById(Long id) {
        return jpaRepository
                .findOne(Specification.where(OcorrenciaSpecs.findByEstaAtivo())
                        .and((root, query, cb) -> cb.equal(root.get("id"), id)))
                .map(mapper::toModel);
    }

    // Método de salvar no banco de dados
    @Override
    public Ocorrencia save(Ocorrencia ocorrencia) {
        OcorrenciaEntity entity = toEntityWithReferences(ocorrencia);
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

        entity.localEndereco = reference(LocalEnderecoEntity.class, ocorrencia.getLocalEndereco().getId());

        if (ocorrencia.getEquipamento() != null) {
            entity.equipamento = reference(EquipamentoEntity.class, ocorrencia.getEquipamento().getId());
        }
        return entity;
    }

    private <T> T reference(Class<T> type, Long id) {
        return entityManager.getReference(type, id);
    }
}
