package com.example.segundoapiappfixa.application.usecase.Ocorrencia;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Ocorrencia;
import com.example.segundoapiappfixa.domain.repository.OcorrenciaRepository;
import com.example.segundoapiappfixa.infrastructure.exception.OcorrenciaNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarOcorrencias {
    private final OcorrenciaRepository ocorrenciaRepository;

    public List<Ocorrencia> listarMinhas(Long usuarioId) {
        return ocorrenciaRepository.findAllByUsuarioId(usuarioId);
    }

    public Ocorrencia listar(Long id, Long usuarioId) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(OcorrenciaNaoEncontradaException::new);

        validarAcesso(ocorrencia, usuarioId);

        return ocorrencia;
    }

    private void validarAcesso(Ocorrencia ocorrencia, Long usuarioId) {
        if (ocorrencia.getUsuario() == null || !usuarioId.equals(ocorrencia.getUsuario().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }
    }
}
