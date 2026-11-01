package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.OrdemServicoMapper;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosOrdemServicoQueryParam;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs.OrdemServicoSpecs;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.utils.SortUtils;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.enums.TipoNotificacao;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.OrdemServicoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ProblemaEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UsuarioEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaOrdemServicoRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaTarefaRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaFotoRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaObservacaoConclusaoRepository;
import com.example.segundoapiappfixa.infrastructure.external.notification.NotificacaoInfrastructureService;
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
import java.time.LocalDateTime;

@Repository
@Transactional
@RequiredArgsConstructor
public class OrdemServicoRepositoryImpl implements OrdemServicoRepository {

    // Dependências
    private final JpaOrdemServicoRepository jpaOrdemServicoRepository;
    private final OrdemServicoMapper ordemServicoMapper;
    private final JpaTarefaRepository jpaTarefaRepository;
    private final JpaFotoRepository jpaFotoRepository;
    private final JpaObservacaoConclusaoRepository jpaObservacaoConclusaoRepository;
    private final NotificacaoInfrastructureService notificacoes;

    @PersistenceContext
    private EntityManager entityManager;

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<OrdemServico> findAllByProblemaIds(List<Long> problemaIds, FiltrosOrdemServicoQueryParam filtros) {
        if (problemaIds.isEmpty()) {
            return List.of();
        }

        return buscar(OrdemServicoSpecs.findByProblemaIds(problemaIds), filtros);
    }

    private List<OrdemServico> buscar(Specification<OrdemServicoEntity> base, FiltrosOrdemServicoQueryParam filtros) {
        Specification<OrdemServicoEntity> specification = base
                .and(OrdemServicoSpecs.findByTitulo(filtros.titulo()))
                .and(OrdemServicoSpecs.findByDescricao(filtros.descricao()))
                .and(OrdemServicoSpecs.findByCategoriaProblema(filtros.categoriaProblema()))
                .and(OrdemServicoSpecs.findByCategoriaEquipamento(filtros.categoriaEquipamento()))
                .and(OrdemServicoSpecs.findByPrioridade(filtros.prioridade()))
                .and(OrdemServicoSpecs.findByStatusOrdemServico(filtros.statusOrdemServico()))
                .and(OrdemServicoSpecs.findByDataPrevistaMin(filtros.dataPrevistaMin()))
                .and(OrdemServicoSpecs.findByDataPrevistaMax(filtros.dataPrevistaMax()))
                .and(OrdemServicoSpecs.findByNomeUsuario(filtros.nomeUsuario()))
                .and(OrdemServicoSpecs.findByTipoAcesso(filtros.tipoAcesso()));

        Sort sort = SortUtils.definirSort(
                SortUtils.normalizarCampoOrdenacao(filtros.campoOrdenacao(), camposNormalizados),
                filtros.direcaoOrdenacao()
        );

        Pageable pageable = filtros.limite() == null ? null :
                PageRequest.of(0, filtros.limite(), sort);

        List<OrdemServicoEntity> entidades = pageable != null
                ? jpaOrdemServicoRepository.findAll(specification, pageable).getContent()
                : jpaOrdemServicoRepository.findAll(specification, sort);

        return entidades
                .stream()
                .map(ordemServicoMapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<OrdemServico> findById(Long id) {
        return jpaOrdemServicoRepository
                .findById(id)
                .map(ordemServicoMapper::toModel);
    }

    @Override
    public boolean existsByProblemaId(Long problemaId) {
        return jpaOrdemServicoRepository.existsByProblema_Id(problemaId);
    }

    // Método de salvar no banco de dados
    @Override
    public OrdemServico save(OrdemServico ordemServico) {
        boolean novaOs = ordemServico.getId() == null;
        Long responsavelAnteriorId = novaOs
                ? null
                : jpaOrdemServicoRepository
                    .findById(ordemServico.getId())
                    .map(entity -> entity.usuario.getId())
                    .orElse(null);

        OrdemServicoEntity entity = ordemServicoMapper.toEntity(ordemServico);
        entity.problema = entityManager.getReference(
                ProblemaEntity.class,
                ordemServico.getProblema().getId()
        );

        entity.usuario = entityManager.getReference(
                UsuarioEntity.class,
                ordemServico.getUsuario().getId()
        );

        OrdemServicoEntity salvo = jpaOrdemServicoRepository.save(entity);
        OrdemServico resultado = ordemServicoMapper.toModel(salvo);

        if (novaOs || !salvo.usuario.getId().equals(responsavelAnteriorId)) {
            notificacoes.notificarAposPersistencia(
                    salvo.usuario.getId(),
                    TipoNotificacao.ORDEM_SERVICO.getId(),
                    salvo.getId(),
                    "Uma nova OS foi atribuída a você: "
                            + ordemServico.getProblema().getTitulo() + ".");
        }

        return resultado;
    }

    // Método de deletar dados persistidos no banco de dados
    @Override
    public void delete(Long id) {
        jpaOrdemServicoRepository.deleteById(id);
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<OrdemServico> findByUsuarioId(Long usuarioId, FiltrosOrdemServicoQueryParam filtros) {
        return buscar(OrdemServicoSpecs.findByUsuarioId(usuarioId), filtros);


    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdemServico> findAtrasadas(LocalDateTime inicioDoDiaAtual) {
        return jpaOrdemServicoRepository.findAllByDataPrevistaBeforeAndStatusOrdemServicoNotIn(
                        inicioDoDiaAtual,
                        List.of(StatusOrdemServico.CONCLUIDA, StatusOrdemServico.ATRASADA))
                .stream()
                .map(ordemServicoMapper::toModel)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdemServico> findTodasAtrasadas() {
        return jpaOrdemServicoRepository.findAllByStatusOrdemServico(StatusOrdemServico.ATRASADA)
                .stream()
                .map(ordemServicoMapper::toModel)
                .toList();
    }
}
