package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.LocalUnidade;
import com.example.segundoapiappfixa.domain.repository.LocalUnidadeRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarLocalUnidade {

    private final LocalUnidadeRepository localUnidadeRepository;
    private final UsuarioRepository usuarioRepository;

    public List<LocalUnidade> listar(Long usuarioId) {
        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        if (usuario.getUnidade() == null || usuario.getUnidade().getId() == null) {
            throw new EntidadeNaoEncontradaException("exception.unidade.notFound");
        }

        return localUnidadeRepository.findAllByUnidadeId(usuario.getUnidade().getId());
    }
}
