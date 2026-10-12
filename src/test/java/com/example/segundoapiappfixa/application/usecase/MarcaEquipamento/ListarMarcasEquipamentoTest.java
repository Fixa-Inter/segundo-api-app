package com.example.segundoapiappfixa.application.usecase.MarcaEquipamento;

import com.example.segundoapiappfixa.domain.repository.MarcaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
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
class ListarMarcasEquipamentoTest {
    @Mock private MarcaEquipamentoRepository repository;
    @InjectMocks private ListarMarcasEquipamento listar;

    @Test
    void deveInformarQuandoMarcaNaoExistir() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException exception = assertThrows(EntidadeNaoEncontradaException.class,
                () -> listar.listar(99L));

        assertEquals("exception.marcaEquipamento.notFound", exception.getMessage());
    }
}
