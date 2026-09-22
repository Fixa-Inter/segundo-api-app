package com.example.segundoapiappfixa.application.usecase.Evento;

import com.example.segundoapiappfixa.adapters.dto.input.Evento.EventoAtualizarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Evento;
import com.example.segundoapiappfixa.domain.repository.EventoRepository;
import com.example.segundoapiappfixa.domain.repository.LocalEnderecoRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AtualizarEvento {
    private final EventoRepository repository;
    private final LocalEnderecoRepository localRepository;

    public Evento atualizar(EventoAtualizarInputDTO dto, Long usuarioId) {
        Evento evento = repository.findById(dto.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.evento.notFound"));

        if (!evento.getUsuario().getId().equals(usuarioId)) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (dto.localEnderecoId() != null) evento.setLocalEndereco(localRepository.findById(dto.localEnderecoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.local.required")));

        if (dto.titulo() != null) evento.setTitulo(dto.titulo());
        if (dto.descricao() != null) evento.setDescricao(dto.descricao());
        if (dto.descricaoLocal() != null) evento.setDescricaoLocal(dto.descricaoLocal());
        if (dto.observacao() != null) evento.setObservacao(dto.observacao());
        if (dto.dataHoraInicio() != null) evento.setDataHoraInicio(dto.dataHoraInicio());
        if (dto.dataHoraFim() != null) evento.setDataHoraFim(dto.dataHoraFim());

        return repository.save(evento);
    }
}
