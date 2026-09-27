package com.example.segundoapiappfixa.domain.repository;


import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosOrdemServicoQueryParam;
import java.util.Map;

import java.util.List;
import java.util.Optional;

public interface OrdemServicoRepository {
    Map<String, String> camposNormalizados = Map.of(
            "titulo", "problema.titulo",
            "descricao", "problema.descricaoProblema",
            "categoria_problema", "categoriaProblema",
            "categoria_equipamento", "problema.categoriaEquipamento.nome",
            "prioridade", "prioridade",
            "data_prevista", "dataPrevista",
            "status", "statusOrdemServico.nome",
            "nome_usuario", "usuario.nomeCompleto",
            "tipo_acesso", "usuario.tipoAcesso"
    );

    List<OrdemServico> findAllByProblemaIds(
            List<Long> problemaIds,
            FiltrosOrdemServicoQueryParam filtros
    );

    Optional<OrdemServico> findById(Long ordemServicoId);

    OrdemServico save(OrdemServico ordemServico);

    void delete(Long ordemServicoId);

    List<OrdemServico> findByUsuarioId(
            Long usuarioId,
            FiltrosOrdemServicoQueryParam filtros
    );
}
