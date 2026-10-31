package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.ProblemaMapper;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosProblemaQueryParam;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs.ProblemaSpecs;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.utils.SortUtils;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.enums.TipoNotificacao;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ProblemaEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaProblemaRepositiory;
import com.example.segundoapiappfixa.infrastructure.external.notification.NotificacaoInfrastructureService;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
public class ProblemaRepositoryImpl implements ProblemaRepository {

    // Dependências
    private final JpaProblemaRepositiory repository;
    private final ProblemaMapper mapper;
    private final NotificacaoInfrastructureService notificacoes;

    @PersistenceContext
    private EntityManager entityManager;


    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Problema> findAllByUsuarioId(Long usuarioId, FiltrosProblemaQueryParam filtros) {
        return buscar(ProblemaSpecs.findByUsuarioId(usuarioId), filtros);
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Problema> findAllByLocalUnidadeIds(List<Long> localUnidadeIds, FiltrosProblemaQueryParam filtros) {

        if (localUnidadeIds.isEmpty()) {
            return List.of();
        }

        return buscar(ProblemaSpecs.findByLocalUnidadeIds(localUnidadeIds), filtros);
    }

    private List<Problema> buscar(Specification<ProblemaEntity> base, FiltrosProblemaQueryParam filtros) {
        Specification<ProblemaEntity> specification = base
                .and(ProblemaSpecs.findByTitulo(filtros.titulo()))
                .and(ProblemaSpecs.findByDescricaoProblema(filtros.descricaoProblema()))
                .and(ProblemaSpecs.findByCategoriaEquipamento(filtros.categoriaEquipamento()))
                .and(ProblemaSpecs.findByLocalUnidade(filtros.localUnidade()))
                .and(ProblemaSpecs.findByDescricaoLocal(filtros.descricaoLocal()))
                .and(ProblemaSpecs.findByNomeUsuario(filtros.nomeUsuario()))
                .and(ProblemaSpecs.findByTipoAcesso(filtros.tipoAcesso()))
                .and(ProblemaSpecs.findByStatusProblema(filtros.statusProblema()))
                .and(ProblemaSpecs.findByDataCriacaoMin(filtros.dataCriacaoMin()))
                .and(ProblemaSpecs.findByDataCriacaoMax(filtros.dataCriacaoMax()));

        Sort sort = SortUtils.definirSort(
                SortUtils.normalizarCampoOrdenacao(filtros.campoOrdenacao(), camposNormalizados),
                filtros.direcaoOrdenacao()
        );

        Pageable pageable = filtros.limite() == null ? null :
                PageRequest.of(0, filtros.limite(), sort);

        List<ProblemaEntity> entidades = pageable != null
                ? repository.findAll(specification, pageable).getContent()
                : repository.findAll(specification, sort);

        return entidades
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<Problema> findById(Long problemaId) {
        return repository.findById(problemaId).map(mapper::toModel);
    }

    // Método de salvar no banco de dados
    @Override
    public Long save(Problema problema, List<String> fotos) {
        return entityManager.unwrap(org.hibernate.Session.class).doReturningWork(connection -> {
            Array urls = connection.createArrayOf("varchar", fotos.toArray(String[]::new));

            try (CallableStatement statement = connection.prepareCall(
                    "CALL cadastrar_problema(?, ?, ?, ?, ?, ?, ?, ?)")
            ) {
                statement.setLong(1, problema.getUsuario().getId());
                statement.setLong(2, problema.getLocalUnidade().getId());
                statement.setLong(3, problema.getCategoriaEquipamento().getId());
                statement.setString(4, problema.getTitulo());
                statement.setString(5, problema.getDescricaoProblema());
                statement.setString(6, problema.getDescricaoLocal());
                statement.registerOutParameter(7, Types.BIGINT);
                statement.setArray(8, urls);
                statement.execute();

                return statement.getLong(7);

            } finally {
                urls.free();
            }
        });
    }

    public Problema update(Problema problema) {
        StatusProblema statusAnterior = repository.findById(problema.getId())
                .map(ProblemaEntity::getStatus).orElse(null);

        ProblemaEntity entity = mapper.toEntity(problema);
        Problema atualizado = mapper.toModel(repository.save(entity));

        if (statusAnterior != null && statusAnterior != atualizado.getStatus() && atualizado.getUsuario() != null) {
            notificacoes.notificarAposPersistencia(atualizado.getUsuario().getId(),
                    TipoNotificacao.SOLICITACAO.getId(), atualizado.getId(),
                    "O status da sua solicitação \"" + atualizado.getTitulo() + "\" foi atualizado para "
                            + atualizado.getStatus().getNome() + ".");
        }
        return atualizado;
    }

    @Override
    public void deleteById(Long problemaId) {
        repository.deleteById(problemaId);
    }

}
