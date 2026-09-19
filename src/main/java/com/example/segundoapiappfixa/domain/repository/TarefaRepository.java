package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Tarefa;

import java.util.List;

public interface TarefaRepository {

    List<Tarefa> findAllByOrdemServicoId(Long id);

    List<Tarefa> saveAll(List<Tarefa> tarefas);

    Long countByOrdemServicoId(Long id);

    Tarefa update(Long id, Tarefa tarefa);

    Tarefa findById(Long id);

    Tarefa deleteById(Long id);
}
