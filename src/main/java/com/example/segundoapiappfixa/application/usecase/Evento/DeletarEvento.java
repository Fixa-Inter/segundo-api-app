package com.example.segundoapiappfixa.application.usecase.Evento;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Evento;
import com.example.segundoapiappfixa.domain.repository.EventoRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeletarEvento {
    private final EventoRepository repository;

    public Evento deletar(Long id, Long usuarioId) {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.evento.notFound"));

        if (!evento.getUsuario().getId().equals(usuarioId)) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return repository.deleteById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.evento.notFound"));
    }
}
