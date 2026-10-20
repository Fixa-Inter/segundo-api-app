package com.example.segundoapiappfixa.application.usecase.Ocorrencia;

import com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia.OcorrenciaCadastrarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.LocalUnidadeRepository;
import com.example.segundoapiappfixa.domain.repository.OcorrenciaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;


@UseCase
@RequiredArgsConstructor
public class CriarOcorrencia {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalUnidadeRepository localUnidadeRepository;
    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;

    public Ocorrencia cadastrar(OcorrenciaCadastrarInputDTO dto, Long usuarioId) {

        if (dto == null) {
            throw new RegraProblemaException("exception.ocorrencia.required");
        }

        LocalUnidade localUnidade = localUnidadeRepository.findById(dto.localUnidadeId())
                .orElseThrow(() -> new RegraProblemaException("exception.local.required"));

        CategoriaEquipamento categoriaEquipamento = categoriaEquipamentoRepository.findById(dto.categoriaEquipamentoId())
                .orElseThrow(() -> new RegraProblemaException("exception.categoriaEquipamento.required"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RegraProblemaException("exception.usuario.required"));

        Long ocorrenciaId = ocorrenciaRepository.save(
                usuarioId,
                dto.localUnidadeId(),
                dto.categoriaEquipamentoId(),
                dto.categoriaProblemaId().getId(),
                dto.prioridade().getId(),
                dto.titulo(),
                dto.descricaoOcorrencia(),
                dto.descricaoLocal(),
                dto.urlsFoto()
        );

        return ocorrenciaRepository.findById(ocorrenciaId)
                .orElseThrow(() -> new RegraProblemaException("exception.ocorrencia.notFound"));
    }
}
