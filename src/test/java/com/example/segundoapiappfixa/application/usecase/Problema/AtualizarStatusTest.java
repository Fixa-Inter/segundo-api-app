package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaAtualizarStatusDTO;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtualizarStatusTest {

    @Mock
    private ProblemaRepository problemaRepository;

    @InjectMocks
    private AtualizarStatus atualizarStatus;

    @Test
    void deveExigirMotivoAoReprovarProblema() {
        Problema problema = problemaPendente();
        when(problemaRepository.findById(1L)).thenReturn(Optional.of(problema));

        ProblemaAtualizarStatusDTO dto = new ProblemaAtualizarStatusDTO(
                1L, StatusProblema.REPROVADO, "   ");

        RegraProblemaException exception = assertThrows(
                RegraProblemaException.class,
                () -> atualizarStatus.atualizar(dto)
        );

        assertEquals("exception.recusa.required", exception.getMessage());
    }

    @Test
    void deveImpedirMotivoQuandoProblemaForAprovado() {
        Problema problema = problemaPendente();
        when(problemaRepository.findById(1L)).thenReturn(Optional.of(problema));

        ProblemaAtualizarStatusDTO dto = new ProblemaAtualizarStatusDTO(
                1L, StatusProblema.APROVADO, "motivo indevido");

        RegraProblemaException exception = assertThrows(
                RegraProblemaException.class,
                () -> atualizarStatus.atualizar(dto)
        );

        assertEquals("exception.recusa.transition", exception.getMessage());
    }

    private Problema problemaPendente() {
        Problema problema = new Problema();
        problema.setId(1L);
        problema.setStatus(StatusProblema.PENDENTE);
        return problema;
    }
}
