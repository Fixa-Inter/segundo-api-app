package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Evento;
import com.example.segundoapiappfixa.domain.filter.FiltrosEventos;
import java.util.Map;

import java.util.List;
import java.util.Optional;

public interface EventoRepository {
    Map<String, String> camposNormalizados = Map.of(
            "titulo", "titulo",
            "descricao", "descricao",
            "local_unidade", "localUnidade.nome",
            "descricao_local", "descricaoLocal",
            "observacao", "observacao",
            "usuario", "usuario.nomeCompleto"
    );

    List<Evento> findByUsuarioId(
            Long usuarioId,
            FiltrosEventos filtros
    );

    Optional<Evento> findById(Long eventoId);
    Evento save(Evento evento);
    Optional<Evento> deleteById(Long eventoId);
}
