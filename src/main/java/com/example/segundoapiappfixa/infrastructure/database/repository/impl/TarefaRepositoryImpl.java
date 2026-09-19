package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.TarefaMapper;
import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.OrdemServicoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.StatusOrdemServicoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.TarefaEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaTarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TarefaRepositoryImpl implements TarefaRepository {

    private final JpaTarefaRepository jpaTarefaRepository;
    private final TarefaMapper tarefaMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public Tarefa findById(Long id) {
        return tarefaMapper.toModel(
                jpaTarefaRepository.findById(id)
                        .orElseThrow()
        );
    }

    @Override
    public List<Tarefa> findAllByOrdemServicoId(Long id) {
        List<TarefaEntity> tarefaEntities = jpaTarefaRepository.findAllByOrdemServico_Id(id);

        return tarefaEntities.stream()
                .map(tarefaMapper::toModel)
                .toList();
    }

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

    public Tarefa update(Long id, Tarefa tarefa) {

        TarefaEntity tarefaEntity = jpaTarefaRepository.findById(id)
                .orElseThrow();

        if (tarefa.getTitulo() != null && !tarefaEntity.getTitulo().equals(tarefa.getTitulo())) {
            tarefaEntity.setTitulo(tarefa.getTitulo());
        }

        if (tarefa.getDescricao() != null && !tarefaEntity.getDescricao().equals(tarefa.getDescricao())) {
            tarefaEntity.setDescricao(tarefa.getDescricao());
        }

        TarefaEntity tarefaUpdated = jpaTarefaRepository.save(tarefaEntity);
        return tarefaMapper.toModel(tarefaUpdated);

    }

    public Tarefa deleteById(Long id) {
        TarefaEntity tarefaEntity = jpaTarefaRepository.findById(id)
                .orElseThrow();

        jpaTarefaRepository.deleteById(id);
        return tarefaMapper.toModel(tarefaEntity);
    }

    public Long countByOrdemServicoId(Long id) {
        return jpaTarefaRepository.countByOrdemServico_Id(id);
    }
}
