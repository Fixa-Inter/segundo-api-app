package com.example.segundoapiappfixa.application.usecase.ModeloEquipamento;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;
import com.example.segundoapiappfixa.domain.repository.ModeloEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeletarModeloEquipamento {

    private final ModeloEquipamentoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public ModeloEquipamento deletar(Long id, Long usuarioId) {

        ModeloEquipamento modelo = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.modeloEquipamento.notFound"));

        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!modelo.getUsuario().getEndereco().getId()
                .equals(usuario.getEndereco().getId())
        ) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return repository.deleteById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.modeloEquipamento.notFound"));
    }
}
