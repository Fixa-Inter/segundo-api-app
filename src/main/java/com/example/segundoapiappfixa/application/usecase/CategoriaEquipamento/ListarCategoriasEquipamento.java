package com.example.segundoapiappfixa.application.usecase.CategoriaEquipamento;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarCategoriasEquipamento {

    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<CategoriaEquipamento> listar(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        List<CategoriaEquipamento> categoriasEquipamento = categoriaEquipamentoRepository
                .findByEnderecoId(usuario.getEndereco().getId());

        if (categoriasEquipamento.isEmpty()) throw new EntidadeNaoEncontradaException("exception.categoriaEquipamento.notFound");

        return categoriasEquipamento;
    }

    public CategoriaEquipamento listar(Long id, Long usuarioId) {

        CategoriaEquipamento categoriaEquipamento = categoriaEquipamentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.categoriaEquipamento.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!categoriaEquipamento.getUsuario().getEndereco().getId()
                .equals(usuario.getEndereco().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return categoriaEquipamento;
    }

}
