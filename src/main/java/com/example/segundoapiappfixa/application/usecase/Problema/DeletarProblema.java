package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeletarProblema {
    private final ProblemaRepository problemaRepository;
    private final FotoRepository fotoRepository;

    @Transactional
    public Problema deletar(Long problemaId, Long usuarioId) {
        Problema problema = problemaRepository.findById(problemaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.problema.notFound"));

        if (!usuarioId.equals(problema.getUsuario().getId())
                || problema.getStatus() != StatusProblema.PENDENTE) {
            throw new RegraProblemaException("exception.access.denied");
        }

        fotoRepository.deleteByProblemaId(problemaId);
        problemaRepository.deleteById(problemaId);
        return problema;
    }
}
