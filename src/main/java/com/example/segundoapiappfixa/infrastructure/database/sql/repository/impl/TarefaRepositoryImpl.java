package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.TarefaMapper;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosTarefaQueryParam;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs.TarefaSpecs;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.utils.SortUtils;
import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.OrdemServicoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.StatusOrdemServicoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.TarefaEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaTarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Repository
@Transactional
@RequiredArgsConstructor
public class TarefaRepositoryImpl implements TarefaRepository {

    // Dependências
    private final JpaTarefaRepository jpaTarefaRepository;
    private final TarefaMapper tarefaMapper;

    @PersistenceContext
    private EntityManager entityManager;

    // Método de listar os registros persistidos no banco de dados
    public Optional<Tarefa> findById(Long id) {
        return jpaTarefaRepository
                .findById(id)
                .map(tarefaMapper::toModel);
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Tarefa> findAllByOrdemServicoId(Long id, FiltrosTarefaQueryParam filtros) {
        Specification<TarefaEntity> specification = Specification.where(TarefaSpecs.findByOrdemServicoId(id))
                .and(TarefaSpecs.findByTituloTarefa(filtros.titulo()))
                .and(TarefaSpecs.findByDescricao(filtros.descricao()))
                .and(TarefaSpecs.findByStatusOrdemServico(filtros.status()))
                .and(TarefaSpecs.findByUsuarioResponsavel(filtros.usuarioResponsavel()));

        Sort sort = SortUtils.definirSort(
                SortUtils.normalizarCampoOrdenacao(filtros.campoOrdenacao(), camposNormalizados),
                filtros.direcaoOrdenacao()
        );

        Pageable pageable = filtros.limite() == null ? null :
                PageRequest.of(0, filtros.limite(), sort);

        List<TarefaEntity> tarefaEntities = pageable != null
                ? jpaTarefaRepository.findAll(specification, pageable).getContent()
                : jpaTarefaRepository.findAll(specification, sort);

        return tarefaEntities
                .stream()
                .map(tarefaMapper::toModel)
                .toList();
    }

    // Método de salvar no banco de dados
    public List<Tarefa> saveAll(List<Tarefa> tarefas) {
        List<TarefaEntity> tarefaEntities = tarefas
                .stream()
                .map(tarefa -> {
                    TarefaEntity entity = tarefaMapper.toEntity(tarefa);

                    entity.ordemServico = entityManager.getReference(
                            OrdemServicoEntity.class,
                            tarefa.getOrdemServico().getId()
                    );

                    entity.statusOrdemServico = entityManager.getReference(
                            StatusOrdemServicoEntity.class,
                            tarefa.getStatusOrdemServico().getId()
                    );

                    return entity;
                })
                .toList();

        return jpaTarefaRepository.saveAll(tarefaEntities)
                .stream()
                .map(tarefaMapper::toModel)
                .toList();
    }

    // Método de salvar no banco de dados
    public Tarefa save(Tarefa tarefa) {
        TarefaEntity tarefaEntity = tarefaMapper.toEntity(tarefa);

        TarefaEntity problemaPersistido = jpaTarefaRepository.save(tarefaEntity);
        return tarefaMapper.toModel(problemaPersistido);
    }

    // Método de deletar dados persistidos no banco de dados
    public Optional<Tarefa> deleteById(Long id) {
        TarefaEntity tarefaEntity = jpaTarefaRepository.findById(id).orElse(null);
        if (tarefaEntity == null) return Optional.empty();

        jpaTarefaRepository.deleteById(id);
        return Optional.of(tarefaMapper.toModel(tarefaEntity));
    }
}
