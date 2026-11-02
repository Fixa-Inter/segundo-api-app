package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.ProblemaMapper;
import com.example.segundoapiappfixa.domain.filter.FiltrosProblema;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaProblemaRepositiory;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProblemaOrdenacaoTest {
    @Test
    void ordenaCampoAssociadoUsandoAliasMesmoComPaginacao() {
        var jpa = mock(JpaProblemaRepositiory.class);
        when(jpa.findAll(any(Specification.class), any(Pageable.class))).thenReturn(new PageImpl<>(List.of()));
        var repository = new ProblemaRepositoryImpl(jpa, mock(ProblemaMapper.class));

        repository.findAllByUsuarioId(1L, filtros("LOCAL_UNIDADE", -1, 5));

        var pagina = ArgumentCaptor.forClass(Pageable.class);
        verify(jpa).findAll(any(Specification.class), pagina.capture());
        var ordem = pagina.getValue().getSort().getOrderFor("localUnidade.nome");
        assertNotNull(ordem);
        assertEquals(Sort.Direction.DESC, ordem.getDirection());
        assertEquals(5, pagina.getValue().getPageSize());
    }

    @Test
    void campoDesconhecidoNaoCriaOrdenacao() {
        var jpa = mock(JpaProblemaRepositiory.class);
        when(jpa.findAll(any(Specification.class), any(Sort.class))).thenReturn(List.of());
        var repository = new ProblemaRepositoryImpl(jpa, mock(ProblemaMapper.class));

        repository.findAllByUsuarioId(1L, filtros("campo_inexistente", 1, null));

        var ordenacao = ArgumentCaptor.forClass(Sort.class);
        verify(jpa).findAll(any(Specification.class), ordenacao.capture());
        assertTrue(ordenacao.getValue().isUnsorted());
    }

    private FiltrosProblema filtros(String campo, Integer direcao, Integer limite) {
        return new FiltrosProblema(null, null, null, null, null, null, null, null,
                null, null, null, limite, campo, direcao);
    }
}
