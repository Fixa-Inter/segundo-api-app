package com.example.segundoapiappfixa.application.usecase.Equipamento;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Equipamento;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.EquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeletarEquipamento {

    private final EquipamentoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public Equipamento deletar(Long id, Long usuarioId) {

        Equipamento equipamento = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.equipamento.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!equipamento.getUsuario().getEndereco().getId().equals(usuario.getEndereco().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return repository.deleteById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.equipamento.notFound"));
    }
}
