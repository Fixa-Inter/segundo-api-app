package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Evento;

import java.util.List;
import java.util.Optional;

public interface EventoRepository {
    List<Evento> findByUsuarioId(Long usuarioId);
    Optional<Evento> findById(Long id);
    Evento save(Evento evento);
    Optional<Evento> deleteById(Long id);
}
