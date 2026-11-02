package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Evento;
import com.example.segundoapiappfixa.domain.filter.FiltrosEventos;

import java.util.List;
import java.util.Optional;

public interface EventoRepository {
    List<Evento> findByUsuarioId(
            Long usuarioId,
            FiltrosEventos filtros
    );

    Optional<Evento> findById(Long eventoId);
    Evento save(Evento evento);
    Optional<Evento> deleteById(Long eventoId);
}
