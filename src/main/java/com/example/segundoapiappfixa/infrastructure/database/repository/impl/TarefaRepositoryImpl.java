package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.TarefaMapper;
import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.TarefaEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaTarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TarefaRepositoryImpl implements TarefaRepository {

    private final JpaTarefaRepository jpaTarefaRepository;
    private final TarefaMapper tarefaMapper;


    @Override
    public List<Tarefa> findAllByOrdemServicoId(Long id) {
        List<TarefaEntity> tarefaEntities = jpaTarefaRepository.findAllByOrdemServico_Id(id);

        return tarefaEntities.stream()
                .map(tarefaMapper::toModel)
                .toList();
    }

    public Long countByOrdemServicoId(Long id) {
        return jpaTarefaRepository.countByOrdemServico_Id(id);
    }
}
