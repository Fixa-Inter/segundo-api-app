package com.example.segundoapiappfixa.application.usecase.Equipamento;

import com.example.segundoapiappfixa.adapters.dto.input.Equipamento.EquipamentoAtualizarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Equipamento;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.EquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.LocalEnderecoRepository;
import com.example.segundoapiappfixa.domain.repository.ModeloEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AtualizarEquipamento {

    private final EquipamentoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ModeloEquipamentoRepository modeloRepository;
    private final LocalEnderecoRepository localRepository;

    public Equipamento atualizar(EquipamentoAtualizarInputDTO dto, Long usuarioId) {

        Equipamento equipamento = repository.findById(dto.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.equipamento.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!equipamento.getUsuario().getEndereco().getId().equals(usuario.getEndereco().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (dto.modeloEquipamentoId() != null) {
            equipamento.setModeloEquipamento(modeloRepository.findById(dto.modeloEquipamentoId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.modeloEquipamento.notFound")));
        }

        if (dto.localEnderecoId() != null) {
            equipamento.setLocalEndereco(localRepository.findById(dto.localEnderecoId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.local.required")));
        }

        if (dto.codigo() != null) equipamento.setCodigo(dto.codigo());

        return repository.save(equipamento);
    }
}
