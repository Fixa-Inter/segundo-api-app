package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosTarefaQueryParam;
import java.util.Map;

import java.util.List;
import java.util.Optional;

public interface TarefaRepository {
    Map<String, String> camposNormalizados = Map.of(
            "titulo", "titulo",
            "descricao", "descricao",
            "status", "statusOrdemServico.nome",
            "usuario_responsavel", "ordemServico.usuario.nomeCompleto"
    );

    List<Tarefa> findAllByOrdemServicoId(Long ordemServicoId, FiltrosTarefaQueryParam filtros);


    List<Tarefa> saveAll(List<Tarefa> tarefas);

    Tarefa save(Tarefa tarefa);

    Optional<Tarefa> findById(Long tarefaId);

    Optional<Tarefa> deleteById(Long tarefaId);
}
