package com.example.segundoapiappfixa.adapters.dto;

import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosProblemaQueryParam;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConversaoEntradasTest {
    @Test
    void converteFiltrosComStatusPeriodoEOrdenacao() {
        LocalDateTime inicio = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime fim = inicio.plusDays(30);
        var filtros = new FiltrosProblemaQueryParam("titulo", null, "categoria", "local",
                null, null, "autor", null, StatusProblema.APROVADO, inicio, fim,
                5, "data_criacao", -1).toDomain();

        assertEquals(StatusProblema.APROVADO, filtros.statusProblema());
        assertEquals(inicio, filtros.dataCriacaoMin());
        assertEquals(fim, filtros.dataCriacaoMax());
        assertEquals("autor", filtros.nomeUsuario());
        assertEquals("data_criacao", filtros.campoOrdenacao());
        assertEquals(-1, filtros.direcaoOrdenacao());
        assertNull(filtros.descricaoProblema());
    }

    @Test
    void preservaCamposOpcionaisEFotosNaAtualizacao() {
        var dados = new ProblemaAtualizarInputDTO(42L, null, "descricao", null, null,
                null, List.of("foto.jpg"), List.of(9L)).toDomain();

        assertEquals(42L, dados.problemaId());
        assertNull(dados.titulo());
        assertEquals("descricao", dados.descricaoProblema());
        assertEquals(List.of("foto.jpg"), dados.fotosAdicionar());
        assertEquals(List.of(9L), dados.fotosExcluir());
    }
}
