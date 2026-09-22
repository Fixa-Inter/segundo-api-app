package com.example.segundoapiappfixa.application.usecase.Ocorrencia;

import com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia.OcorrenciaCadastrarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.EquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.LocalEnderecoRepository;
import com.example.segundoapiappfixa.domain.repository.OcorrenciaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class CriarOcorrencia {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalEnderecoRepository localEnderecoRepository;
    private final EquipamentoRepository equipamentoRepository;

    public Ocorrencia cadastrar(OcorrenciaCadastrarInputDTO dto, Long usuarioId) {

        if (dto == null) {
            throw new RegraProblemaException("exception.ocorrencia.required");
        }

        LocalEndereco localEndereco = localEnderecoRepository.findById(dto.localEnderecoId())
                .orElseThrow(() -> new RegraProblemaException("exception.local.required"));

        Equipamento equipamento = equipamentoRepository.findById(dto.equipamentoId())
                .orElseThrow(() -> new RegraProblemaException("exception.equipamento.required"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RegraProblemaException("exception.usuario.required"));

        Ocorrencia ocorrencia = new Ocorrencia(
                null,
                usuario,
                localEndereco,
                equipamento,
                CategoriaProblema.fromId(dto.categoriaProblemaId().intValue()),
                Prioridade.fromId(dto.prioridade().intValue()),
                dto.titulo(),
                dto.descricaoOcorrencia(),
                dto.descricaoLocal(),
                LocalDateTime.now(),
                true
        );

        return ocorrenciaRepository.save(ocorrencia);
    }
}
