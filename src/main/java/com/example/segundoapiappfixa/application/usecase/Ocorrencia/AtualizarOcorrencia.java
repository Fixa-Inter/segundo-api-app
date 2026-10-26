package com.example.segundoapiappfixa.application.usecase.Ocorrencia;

import com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia.OcorrenciaAtualizarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.OcorrenciaRepository;
import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.LocalUnidadeRepository;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.domain.enums.TipoFoto;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class AtualizarOcorrencia {
    private final OcorrenciaRepository ocorrenciaRepository;
    private final LocalUnidadeRepository localUnidadeRepository;
    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final FotoRepository fotoRepository;

    @Transactional
    public Ocorrencia atualizar(OcorrenciaAtualizarInputDTO dto, Long usuarioId) {

        Long id = dto.id();
        Ocorrencia atual = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.ocorrencia.notFound"));

        if (atual.getUsuario() == null || !usuarioId.equals(atual.getUsuario().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (dto.localUnidadeId() != null) {
            atual.setLocalUnidade(localUnidadeRepository.findById(dto.localUnidadeId())
                    .orElseThrow(() -> new RegraProblemaException("exception.local.required")));
        }

        if (dto.categoriaEquipamentoId() != null) {
            atual.setCategoriaEquipamento(categoriaEquipamentoRepository.findById(dto.categoriaEquipamentoId())
                    .orElseThrow(() -> new RegraProblemaException("exception.categoriaEquipamento.required")));
        }

        if (dto.categoriaProblemaId() != null) atual.setCategoriaProblema(dto.categoriaProblemaId());

        if (dto.prioridade() != null) atual.setPrioridade(dto.prioridade());

        if (dto.titulo() != null) atual.setTitulo(dto.titulo());

        if (dto.descricaoOcorrencia() != null) atual.setDescricaoOcorrencia(dto.descricaoOcorrencia());

        if (dto.descricaoLocal() != null) atual.setDescricaoLocal(dto.descricaoLocal());

        atual = ocorrenciaRepository.update(atual);

        if (dto.fotosExcluir() != null) {
            Long ocorrenciaId = atual.getId();
            List<Long> idsPertencentes = atual.getFotos() == null ? List.of() : atual.getFotos().stream()
                    .map(Foto::getId)
                    .filter(idFoto -> idFoto != null && dto.fotosExcluir().contains(idFoto))
                    .toList();
            idsPertencentes.forEach(idFoto -> fotoRepository.deleteByIdAndOcorrenciaId(idFoto, ocorrenciaId));
            if (atual.getFotos() != null) {
                atual.getFotos().removeIf(foto -> foto.getId() != null && idsPertencentes.contains(foto.getId()));
            }
        }

        if (dto.fotosAdicionar() != null && !dto.fotosAdicionar().isEmpty()) {
            if (atual.getFotos() == null) atual.setFotos(new ArrayList<>());
            for (String url : dto.fotosAdicionar()) {
                Foto foto = new Foto(
                        null,
                        null,
                        atual,
                        null,
                        null,
                        atual.getUsuario(),
                        url,
                        true,
                        LocalDateTime.now()
                );

                atual.getFotos().add(fotoRepository.save(foto, TipoFoto.OCORRENCIA));
            }
        }
        return atual;
    }
}
