package com.example.segundoapiappfixa.application.usecase.ModeloEquipamento;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.ModeloEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarModelosEquipamentos {
    private final ModeloEquipamentoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public List<ModeloEquipamento> listar(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        List<ModeloEquipamento> modelos = repository.findByEnderecoId(
                usuario.getEndereco().getId()
        );

        if (modelos.isEmpty()) throw new EntidadeNaoEncontradaException("exception.modeloEquipamento.notFound");

        return modelos;
    }

    public ModeloEquipamento listar(Long id, Long usuarioId) {

        ModeloEquipamento modelo = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.modeloEquipamento.notFound"));

        validarEndereco(modelo, usuarioId);

        return modelo;
    }

    private void validarEndereco(ModeloEquipamento modelo, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!modelo.getUsuario().getEndereco().getId()
                .equals(usuario.getEndereco().getId())
        ) {
            throw new RegraProblemaException("exception.access.denied");
        }

    }
}
