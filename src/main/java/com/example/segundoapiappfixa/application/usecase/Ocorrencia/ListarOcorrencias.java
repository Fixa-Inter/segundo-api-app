package com.example.segundoapiappfixa.application.usecase.Ocorrencia;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.LocalEndereco;
import com.example.segundoapiappfixa.domain.model.Ocorrencia;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.LocalEnderecoRepository;
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
    private final LocalEnderecoRepository localEnderecoRepository;

    public List<Ocorrencia> listar(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        List<Long> localEnderecoIds = localEnderecoRepository
                .findAllByEnderecoId(usuario.getEndereco().getId())
                .stream()
                .map(LocalEndereco::getId)
                .toList();

        List<Ocorrencia> ocorrencias = ocorrenciaRepository
                .findAllByLocalEnderecoIds(localEnderecoIds);

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

    public List<Ocorrencia> listarMinhas(Long usuarioId) {
        return ocorrenciaRepository.findAllByUsuarioId(usuarioId);
    }

    private void validarAcesso(Ocorrencia ocorrencia, Long usuarioId) {
        if (ocorrencia.getUsuario() == null || !usuarioId.equals(ocorrencia.getUsuario().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }
    }
}
