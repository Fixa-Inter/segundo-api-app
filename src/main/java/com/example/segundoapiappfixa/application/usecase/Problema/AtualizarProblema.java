package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.domain.model.input.Problema.ProblemaAtualizarDados;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.enums.TipoFoto;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.*;
import com.example.segundoapiappfixa.application.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class AtualizarProblema {
    private final ProblemaRepository problemaRepository;
    private final FotoRepository fotoRepository;
    private final LocalUnidadeRepository localUnidadeRepository;
    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;

    @Transactional
    public Problema atualizar(ProblemaAtualizarDados dto, Long usuarioId) {
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

        Problema salvo = problemaRepository.update(problema);
        if (dto.fotosExcluir() != null) {
            Long ocorrenciaId = salvo.getId();
            List<Long> idsPertencentes = salvo.getFotos() == null ? List.of() : salvo.getFotos().stream()
                    .map(Foto::getId)
                    .filter(idFoto -> idFoto != null && dto.fotosExcluir().contains(idFoto))
                    .toList();

            idsPertencentes.forEach(idFoto -> fotoRepository.deleteByIdAndOcorrenciaId(idFoto, ocorrenciaId));

            if (salvo.getFotos() != null) {
                salvo.getFotos().removeIf(foto -> foto.getId() != null && idsPertencentes.contains(foto.getId()));
            }
        }

        if (dto.fotosAdicionar() != null && !dto.fotosAdicionar().isEmpty()) {
            if (salvo.getFotos() == null) salvo.setFotos(new ArrayList<>());
            for (String url : dto.fotosAdicionar()) {
                Foto foto = new Foto(
                        null,
                        salvo,
                        null,
                        null,
                        null,
                        salvo.getUsuario(),
                        url,
                        true,
                        LocalDateTime.now()
                );

                salvo.getFotos().add(fotoRepository.save(foto, TipoFoto.OCORRENCIA));
            }
        }

        return salvo;
    }
}
