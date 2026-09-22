package com.example.segundoapiappfixa.application.usecase.Evento;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Evento;
import com.example.segundoapiappfixa.domain.repository.EventoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarEventos {
    private final EventoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public List<Evento> listar(Long usuarioId) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        List<Evento> eventos = repository.findByUsuarioId(usuarioId);
        if (eventos.isEmpty()) throw new EntidadeNaoEncontradaException("exception.evento.notFound");

        return eventos;
    }

    public Evento listar(Long id, Long usuarioId) {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.evento.notFound"));

        if (!evento.getUsuario().getId().equals(usuarioId)) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return evento;
    }
}
