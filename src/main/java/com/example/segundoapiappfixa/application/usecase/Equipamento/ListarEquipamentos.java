package com.example.segundoapiappfixa.application.usecase.Equipamento;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Equipamento;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.EquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarEquipamentos {

    private final EquipamentoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public List<Equipamento> listar(Long modeloEquipamentoId, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        List<Equipamento> equipamentos = repository.findByModeloEquipamentoId(modeloEquipamentoId)
                .stream()
                .filter(equipamento -> equipamento.getUsuario().getEndereco().getId()
                        .equals(usuario.getEndereco().getId()))
                .toList();

        if (equipamentos.isEmpty()) {
            throw new EntidadeNaoEncontradaException("exception.equipamento.notFound");
        }
        return equipamentos;
    }

    public Equipamento listarDetalhes(Long id, Long usuarioId) {
        Equipamento equipamento = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.equipamento.notFound"));

        validarAcesso(equipamento, usuarioId);
        return equipamento;
    }

    private void validarAcesso(Equipamento equipamento, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!equipamento.getUsuario().getEndereco().getId().equals(usuario.getEndereco().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }
    }
}
