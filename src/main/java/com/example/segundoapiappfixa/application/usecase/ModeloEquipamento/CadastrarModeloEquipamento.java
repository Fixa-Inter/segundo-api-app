package com.example.segundoapiappfixa.application.usecase.ModeloEquipamento;

import com.example.segundoapiappfixa.adapters.dto.input.ModeloEquipamento.ModeloEquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.*;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class CadastrarModeloEquipamento {

    private final ModeloEquipamentoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final MarcaEquipamentoRepository marcaRepository;
    private final CategoriaEquipamentoRepository categoriaRepository;

    public ModeloEquipamento cadastrar(ModeloEquipamentoCadastrarInputDTO dto, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        MarcaEquipamento marca = marcaRepository.findById(dto.marcaEquipamentoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.marcaEquipamento.notFound"));

        CategoriaEquipamento categoria = categoriaRepository.findById(dto.categoriaEquipamentoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.categoriaEquipamento.notFound"));

        ModeloEquipamento modeloEquipamento = new ModeloEquipamento(
                null,
                usuario,
                marca,
                categoria,
                dto.nome(),
                dto.descricao(),
                LocalDateTime.now(),
                true
        );

        return repository.save(modeloEquipamento);
    }
}
