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
            "local_endereco", "localEndereco.nome",
            "descricao_local", "descricaoLocal",
            "nome_usuario", "usuario.nomeCompleto",
            "tipo_acesso", "usuario.tipoAcesso"
    );

    List<Problema> findAllByUsuarioId(
            Long usuarioId,
            FiltrosProblemaQueryParam filtros
    );

    List<Problema> findAllByLocalEnderecoIds(
            List<Long> localEnderecoIds,
            FiltrosProblemaQueryParam filtros
    );

    Optional<Problema> findById(Long problemaId);

    Problema save(Problema problema);

}
