package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.domain.filter.FiltrosTarefa;
import java.util.Map;
import java.util.Collection;

import java.util.List;
import java.util.Optional;

public interface TarefaRepository {
    Map<String, String> camposNormalizados = Map.of(
            "titulo", "titulo",
            "descricao", "descricao",
            "status", "statusOrdemServico.nome",
            "usuario_responsavel", "ordemServico.usuario.nomeCompleto"
    );

    List<Tarefa> findAllByOrdemServicoId(Long ordemServicoId, FiltrosTarefa filtros);

    Map<Long, Long> countEmAndamentoByTecnicoIds(Collection<Long> tecnicoIds);


    List<Tarefa> saveAll(List<Tarefa> tarefas);

    Tarefa save(Tarefa tarefa);

    Optional<Tarefa> findById(Long tarefaId);

    Optional<Tarefa> deleteById(Long tarefaId);

    void deleteByOrdemServicoId(Long ordemServicoId);
}
