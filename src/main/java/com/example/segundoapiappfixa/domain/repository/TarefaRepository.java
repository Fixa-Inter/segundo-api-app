package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Tarefa;

import java.util.List;
import java.util.Optional;

public interface TarefaRepository {

    List<Tarefa> findAllByOrdemServicoId(Long id);

    List<Tarefa> saveAll(List<Tarefa> tarefas);

    Long countByOrdemServicoId(Long id);

    Tarefa save(Tarefa tarefa);

    Optional<Tarefa> findById(Long id);

    Optional<Tarefa> deleteById(Long id);
}
