package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaAtualizarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.enums.TipoFoto;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.*;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@UseCase
@RequiredArgsConstructor
public class AtualizarProblema {
    private final ProblemaRepository problemaRepository;
    private final FotoRepository fotoRepository;
    private final LocalUnidadeRepository localUnidadeRepository;
    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;

    @Transactional
    public Problema atualizar(ProblemaAtualizarInputDTO dto, Long usuarioId) {
        Problema problema = problemaRepository.findById(dto.problemaId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.problema.notFound"));

        if (!usuarioId.equals(problema.getUsuario().getId())
                || problema.getStatus() != StatusProblema.PENDENTE) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (dto.titulo() != null && !dto.titulo().isBlank()) problema.setTitulo(dto.titulo());
        if (dto.descricaoProblema() != null && !dto.descricaoProblema().isBlank()) problema.setDescricaoProblema(dto.descricaoProblema());
        if (dto.descricaoLocal() != null && !dto.descricaoLocal().isBlank()) problema.setDescricaoLocal(dto.descricaoLocal());

        if (dto.localUnidadeId() != null) problema.setLocalUnidade(
                localUnidadeRepository.findById(dto.localUnidadeId())
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.localUnidade.notFound")));

        if (dto.categoriaEquipamentoId() != null) problema.setCategoriaEquipamento(
                categoriaEquipamentoRepository.findById(dto.categoriaEquipamentoId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.categoriaEquipamento.notFound")));

        Problema salvo = problemaRepository.save(problema);
        if (dto.fotosExcluir() != null) dto.fotosExcluir().forEach(fotoRepository::deleteById);

        if (dto.fotosAdicionar() != null) {
            if (salvo.getFotos() == null) salvo.setFotos(new ArrayList<>());
            dto.fotosAdicionar().forEach(url -> salvo.getFotos().add(fotoRepository.save(
                    new Foto(
                        null,
                        salvo,
                        null,
                        null,
                        null,
                        null,
                        url,
                        true,
                        LocalDateTime.now()),
                        TipoFoto.PROBLEMA
            )));
        }

        return salvo;
    }
}
