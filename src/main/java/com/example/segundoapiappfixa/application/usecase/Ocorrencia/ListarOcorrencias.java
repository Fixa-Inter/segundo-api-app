package com.example.segundoapiappfixa.application.usecase.Ocorrencia;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.LocalUnidade;
import com.example.segundoapiappfixa.domain.model.Ocorrencia;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosOcorrenciaQueryParam;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.LocalUnidadeRepository;
import com.example.segundoapiappfixa.domain.repository.OcorrenciaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarOcorrencias {
    private final OcorrenciaRepository ocorrenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalUnidadeRepository localUnidadeRepository;

    public List<Ocorrencia> listar(Long usuarioId, FiltrosOcorrenciaQueryParam filtros) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        List<Long> localUnidadeIds = localUnidadeRepository
                .findAllByUnidadeId(usuario.getUnidade().getId())
                .stream()
                .map(LocalUnidade::getId)
                .toList();

        List<Ocorrencia> ocorrencias = ocorrenciaRepository
                .findAllByLocalUnidadeIds(localUnidadeIds, filtros);

        if (ocorrencias.isEmpty()) {
            throw new EntidadeNaoEncontradaException("exception.ocorrencia.notFound");
        }

        return ocorrencias;
    }

    public Ocorrencia listar(Long id, Long usuarioId) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException("exception.ocorrencia.notFound"));

        validarAcesso(ocorrencia, usuarioId);

        return ocorrencia;
    }

    public List<Ocorrencia> listarMinhas(Long usuarioId, FiltrosOcorrenciaQueryParam filtros) {
        return ocorrenciaRepository.findAllByUsuarioId(usuarioId, filtros);
    }

    private void validarAcesso(Ocorrencia ocorrencia, Long usuarioId) {
        if (ocorrencia.getUsuario() == null || !usuarioId.equals(ocorrencia.getUsuario().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }
    }
}
