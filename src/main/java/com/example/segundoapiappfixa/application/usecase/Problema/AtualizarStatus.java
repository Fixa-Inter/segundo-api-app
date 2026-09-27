package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaAtualizarStatusDTO;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class AtualizarStatus {

    private final ProblemaRepository problemaRepository;
    public Problema atualizar(ProblemaAtualizarStatusDTO dto) {

        Problema problema = problemaRepository.findById(dto.problemaId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.problema.notFound"));

        if (problema.getStatus() != StatusProblema.PENDENTE || dto.statusProblema() == StatusProblema.PENDENTE)
            throw new RegraProblemaException("exception.status.transition");

        if (dto.statusProblema() == StatusProblema.APROVADO && dto.motivoRecusa() != null) {
            throw new RegraProblemaException("exception.recusa.transition");
        }

        problema.setStatus(dto.statusProblema());
        problema.setMotivoRecusa(dto.motivoRecusa());

        return problemaRepository.save(problema);
    }
}
