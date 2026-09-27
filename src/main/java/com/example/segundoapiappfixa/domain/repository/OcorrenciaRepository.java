package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Ocorrencia;
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
            "local_endereco", "localEndereco.nome",
            "descricao_local", "descricaoLocal",
            "equipamento_codigo", "equipamento.codigo",
            "nome_usuario", "usuario.nomeCompleto",
            "tipo_acesso", "usuario.tipoAcesso"
    );

    List<Ocorrencia> findAllByUsuarioId(
            Long usuarioId,
            FiltrosOcorrenciaQueryParam filtros
    );

    List<Ocorrencia> findAllByLocalEnderecoIds(
            List<Long> localEnderecoIds,
            FiltrosOcorrenciaQueryParam filtros
    );

    Optional<Ocorrencia> findById(Long ocorrenciaId);

    Ocorrencia save(Ocorrencia ocorrencia);

    Optional<Ocorrencia> deleteById(Long ocorrenciaId);
}
