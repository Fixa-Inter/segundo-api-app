package com.example.segundoapiappfixa.application.usecase.ModeloEquipamento;

import com.example.segundoapiappfixa.domain.model.input.ModeloEquipamento.ModeloEquipamentoCadastrarDados;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.application.security.UnidadeAcesso;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.*;
import com.example.segundoapiappfixa.application.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class CadastrarModeloEquipamento {

    private final ModeloEquipamentoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final MarcaEquipamentoRepository marcaRepository;
    private final CategoriaEquipamentoRepository categoriaRepository;

    public ModeloEquipamento cadastrar(ModeloEquipamentoCadastrarDados dto, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        MarcaEquipamento marca = marcaRepository.findById(dto.marcaEquipamentoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.marcaEquipamento.notFound"));

        CategoriaEquipamento categoria = categoriaRepository.findById(dto.categoriaEquipamentoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.categoriaEquipamento.notFound"));

        UnidadeAcesso.validarCategoria(usuario, categoria);

        ModeloEquipamento modeloEquipamento = new ModeloEquipamento(
                null,
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
