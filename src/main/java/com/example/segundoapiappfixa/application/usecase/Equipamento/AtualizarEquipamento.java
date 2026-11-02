package com.example.segundoapiappfixa.application.usecase.Equipamento;

import com.example.segundoapiappfixa.domain.model.input.Equipamento.EquipamentoAtualizarDados;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Equipamento;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.EquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.LocalUnidadeRepository;
import com.example.segundoapiappfixa.domain.repository.ModeloEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.application.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AtualizarEquipamento {

    private final EquipamentoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ModeloEquipamentoRepository modeloRepository;
    private final LocalUnidadeRepository localRepository;

    public Equipamento atualizar(EquipamentoAtualizarDados dto, Long usuarioId) {

        Equipamento equipamento = repository.findById(dto.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.equipamento.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!equipamento.getUsuario().getUnidade().getId().equals(usuario.getUnidade().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (dto.modeloEquipamentoId() != null) {
            equipamento.setModeloEquipamento(modeloRepository.findById(dto.modeloEquipamentoId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.modeloEquipamento.notFound")));
        }

        if (dto.localUnidadeId() != null) {
            equipamento.setLocalUnidade(localRepository.findById(dto.localUnidadeId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.local.required")));
        }

        if (dto.codigo() != null) equipamento.setCodigo(dto.codigo());

        return repository.save(equipamento);
    }
}
