package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosProblemaQueryParam;

import java.util.Map;

import java.util.List;
import java.util.Optional;

public interface ProblemaRepository {
    Map<String, String> camposNormalizados = Map.of(
            "titulo", "titulo",
            "descricao_problema", "descricaoProblema",
            "categoria_equipamento", "categoriaEquipamento.nome",
            "local_unidade", "localUnidade.nome",
            "descricao_local", "descricaoLocal",
            "nome_usuario", "usuario.nomeCompleto",
            "tipo_acesso", "usuario.tipoAcesso"
    );

    List<Problema> findAllByUsuarioId(
            Long usuarioId,
            FiltrosProblemaQueryParam filtros
    );

    List<Problema> findAllByLocalUnidadeIds(
            List<Long> localUnidadeIds,
            FiltrosProblemaQueryParam filtros
    );

    Optional<Problema> findById(Long problemaId);

    Long save(
            Long usuarioId,
            Long localUnidadeId,
            Long categoriaEquipamentoId,
            String titulo,
            String descricaoProblema,
            String descricaoLocal,
            List<String> fotos
    );

    void deleteById(Long problemaId);

}
