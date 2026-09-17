package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Tarefa;

import java.util.List;

public interface TarefaRepository {

    List<Tarefa> findAllByOrdemServicoId(Long id);

    Long countByOrdemServicoId(Long id);
}
