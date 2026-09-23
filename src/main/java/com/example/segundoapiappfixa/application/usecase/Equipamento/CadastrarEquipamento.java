package com.example.segundoapiappfixa.application.usecase.Equipamento;

import com.example.segundoapiappfixa.adapters.dto.input.Equipamento.EquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Equipamento;
import com.example.segundoapiappfixa.domain.model.LocalEndereco;
import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.EquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.LocalEnderecoRepository;
import com.example.segundoapiappfixa.domain.repository.ModeloEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class CadastrarEquipamento {

    private final EquipamentoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ModeloEquipamentoRepository modeloRepository;
    private final LocalEnderecoRepository localRepository;

    public Equipamento cadastrar(EquipamentoCadastrarInputDTO dto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        ModeloEquipamento modelo = modeloRepository.findById(dto.modeloEquipamentoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.modeloEquipamento.notFound"));

        LocalEndereco local = localRepository.findById(dto.localEnderecoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.local.required"));

        Equipamento equipamento = new Equipamento(
                null,
                usuario,
                modelo,
                local,
                dto.codigo(),
                true,
                LocalDateTime.now()
        );

        return repository.save(equipamento);
    }
}
