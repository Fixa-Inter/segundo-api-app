package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaAtualizarStatusDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaDetalhesOutputDTO;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.infrastructure.exception.ProblemaNaoEncontradoException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Foto;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class AtualizarStatus {

    private final ProblemaRepository problemaRepository;
    private final FotoRepository fotoRepository;

    public ProblemaDetalhesOutputDTO atualizar(ProblemaAtualizarStatusDTO dto, Boolean isGestor) {

        if (!isGestor) throw new RegraProblemaException("exception.gestor.required");

        Problema problema = problemaRepository.findById(dto.problemaId())
                .orElseThrow(ProblemaNaoEncontradoException::new);

        if (problema.getStatus() != StatusProblema.PENDENTE || dto.statusProblema() == StatusProblema.PENDENTE)
            throw new RegraProblemaException("exception.status.transition");

        if (dto.statusProblema() == StatusProblema.APROVADO && dto.motivoRecusa() != null) {
            throw new RegraProblemaException("exception.recusa.transition");
        }

        problema.setStatus(dto.statusProblema());
        problema.setMotivoRecusa(dto.motivoRecusa());
        problemaRepository.save(problema);

        List<String> urlFotos = fotoRepository
                .findAllByProblemaId(dto.problemaId())
                .stream()
                .map(Foto::getUrl)
                .toList();

        return new ProblemaDetalhesOutputDTO(
                problema.getTitulo(),
                problema.getDescricaoProblema(),
                problema.getLocalEndereco().getNome(),
                problema.getDescricaoLocal(),
                problema.getCategoriaEquipamento().getNome(),
                problema.getStatus().getNome(),
                urlFotos
        );
    }
}
