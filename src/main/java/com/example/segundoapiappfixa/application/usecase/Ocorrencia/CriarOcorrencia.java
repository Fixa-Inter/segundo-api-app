package com.example.segundoapiappfixa.application.usecase.Ocorrencia;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.LocalUnidadeRepository;
import com.example.segundoapiappfixa.domain.repository.OcorrenciaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class CriarOcorrencia {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalUnidadeRepository localUnidadeRepository;
    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;

    public Ocorrencia cadastrar(Ocorrencia ocorrencia, List<String> fotos, Long usuarioId) {
        if (ocorrencia == null) {
            throw new RegraProblemaException("exception.ocorrencia.required");
        }

        Long localUnidadeId = ocorrencia.getLocalUnidade() == null ? null : ocorrencia.getLocalUnidade().getId();
        Long categoriaEquipamentoId = ocorrencia.getCategoriaEquipamento() == null
                ? null : ocorrencia.getCategoriaEquipamento().getId();

        LocalUnidade localUnidade = localUnidadeRepository.findById(localUnidadeId)
                .orElseThrow(() -> new RegraProblemaException("exception.local.required"));

        CategoriaEquipamento categoriaEquipamento = categoriaEquipamentoRepository.findById(categoriaEquipamentoId)
                .orElseThrow(() -> new RegraProblemaException("exception.categoriaEquipamento.required"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RegraProblemaException("exception.usuario.required"));

        ocorrencia.setUsuario(usuario);
        ocorrencia.setLocalUnidade(localUnidade);
        ocorrencia.setCategoriaEquipamento(categoriaEquipamento);

        Long ocorrenciaId = ocorrenciaRepository.save(ocorrencia, fotos);

        return ocorrenciaRepository.findById(ocorrenciaId)
                .orElseThrow(() -> new RegraProblemaException("exception.ocorrencia.notFound"));
    }
}
