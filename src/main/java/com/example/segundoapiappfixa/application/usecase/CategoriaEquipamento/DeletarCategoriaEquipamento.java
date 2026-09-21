package com.example.segundoapiappfixa.application.usecase.CategoriaEquipamento;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeletarCategoriaEquipamento {

    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public CategoriaEquipamento deletar(Long categoriaId, Long usuarioId) {
        CategoriaEquipamento categoriaEquipamento = categoriaEquipamentoRepository.findById(categoriaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.categoriaEquipamento.notFound"));

        Usuario usuarioAtual = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!categoriaEquipamento.getUsuario().getEndereco().getId()
                .equals(usuarioAtual.getEndereco().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return categoriaEquipamentoRepository.deleteById(categoriaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.categoriaEquipamento.notFound"));
    }
}
