package com.example.segundoapiappfixa.application.usecase.Equipamento;

import com.example.segundoapiappfixa.domain.repository.EquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
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
class ListarEquipamentosTest {
    @Mock private EquipamentoRepository repository;
    @Mock private UsuarioRepository usuarioRepository;
    @InjectMocks private ListarEquipamentos listar;

    @Test
    void deveInformarQuandoEquipamentoNaoExistir() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException exception = assertThrows(EntidadeNaoEncontradaException.class,
                () -> listar.listarDetalhes(99L, 1L));

        assertEquals("exception.equipamento.notFound", exception.getMessage());
    }
}
