package com.example.segundoapiappfixa.application.usecase.ModeloEquipamento;

import com.example.segundoapiappfixa.adapters.dto.input.ModeloEquipamento.ModeloEquipamentoAtualizarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.*;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AtualizarModeloEquipamento {

    private final ModeloEquipamentoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final MarcaEquipamentoRepository marcaRepository;
    private final CategoriaEquipamentoRepository categoriaRepository;

    public ModeloEquipamento atualizar(ModeloEquipamentoAtualizarInputDTO dto, Long usuarioId) {

        ModeloEquipamento modelo = repository.findById(dto.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.modeloEquipamento.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!modelo.getUsuario().getEndereco().getId()
                .equals(usuario.getEndereco().getId())
        ) throw new RegraProblemaException("exception.access.denied");

        if (dto.marcaEquipamentoId() != null) {
            modelo.setMarcaEquipamento(
                    marcaRepository.findById(dto.marcaEquipamentoId())
                            .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.marcaEquipamento.notFound")));
        }

        if (dto.categoriaEquipamentoId() != null) {
            modelo.setCategoriaEquipamento(
                    categoriaRepository.findById(dto.categoriaEquipamentoId())
                            .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.categoriaEquipamento.notFound")));
        }

        if (dto.nome() != null) modelo.setNome(dto.nome());
        if (dto.descricao() != null) modelo.setDescricao(dto.descricao());
        if (dto.estaAtivo() != null) modelo.setEstaAtivo(dto.estaAtivo());

        return repository.save(modelo);
    }
}
