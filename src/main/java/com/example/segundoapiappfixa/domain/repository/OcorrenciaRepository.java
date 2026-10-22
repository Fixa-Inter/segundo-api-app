package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Ocorrencia;
import java.util.List;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosOcorrenciaQueryParam;
import java.util.Map;

import java.util.List;
import java.util.Optional;

public interface OcorrenciaRepository {
    Map<String, String> camposNormalizados = Map.of(
            "titulo", "titulo",
            "descricao_ocorrencia", "descricaoOcorrencia",
            "categoria_problema", "categoriaProblema",
            "prioridade", "prioridade",
            "local_unidade", "localUnidade.nome",
            "descricao_local", "descricaoLocal",
            "categoria_equipamento", "categoriaEquipamento.nome",
            "nome_usuario", "usuario.nomeCompleto",
            "tipo_acesso", "usuario.tipoAcesso"
    );

    List<Ocorrencia> findAllByUsuarioId(
            Long usuarioId,
            FiltrosOcorrenciaQueryParam filtros
    );

    List<Ocorrencia> findAllByLocalUnidadeIds(
            List<Long> localUnidadeIds,
            FiltrosOcorrenciaQueryParam filtros
    );

    Optional<Ocorrencia> findById(Long ocorrenciaId);

    Long save(Ocorrencia ocorrencia, List<String> fotos);

    Ocorrencia update(Ocorrencia ocorrencia);

    Optional<Ocorrencia> deleteById(Long ocorrenciaId);
}
