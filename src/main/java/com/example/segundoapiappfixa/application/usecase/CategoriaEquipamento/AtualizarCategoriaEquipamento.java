package com.example.segundoapiappfixa.application.usecase.CategoriaEquipamento;

import com.example.segundoapiappfixa.adapters.dto.input.CategoriaEquipamento.CategoriaEquipamentoAtualizarInputDTO;
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
public class AtualizarCategoriaEquipamento {

    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public CategoriaEquipamento atualizar(
            CategoriaEquipamentoAtualizarInputDTO dto,
            Long usuarioId
    ) {
        CategoriaEquipamento categoriaEquipamento = categoriaEquipamentoRepository.findById(dto.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.categoriaEquipamento.notFound"));

        Usuario usuarioAtual = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!categoriaEquipamento.getUsuario().getEndereco().getId()
                .equals(usuarioAtual.getEndereco().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (dto.nome() != null) categoriaEquipamento.setNome(dto.nome());
        if (dto.descricao() != null) categoriaEquipamento.setDescricao(dto.descricao());
        if (dto.estaAtivo() != null) categoriaEquipamento.setEstaAtivo(dto.estaAtivo());

        return categoriaEquipamentoRepository.save(categoriaEquipamento);
    }
}
