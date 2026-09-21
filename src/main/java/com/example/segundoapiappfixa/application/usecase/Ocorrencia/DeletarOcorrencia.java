package com.example.segundoapiappfixa.application.usecase.Ocorrencia;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Ocorrencia;
import com.example.segundoapiappfixa.domain.repository.OcorrenciaRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeletarOcorrencia {
    private final OcorrenciaRepository ocorrenciaRepository;

    public Ocorrencia deletar(Long id, Long usuarioId) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.ocorrencia.notFound"));

        if (ocorrencia.getUsuario() == null || !usuarioId.equals(ocorrencia.getUsuario().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return ocorrenciaRepository.deleteById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.ocorrencia.notFound"));
    }
}
