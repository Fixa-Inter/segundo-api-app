package com.example.segundoapiappfixa.application.usecase.CategoriaEquipamento;

import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
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
class ListarCategoriasEquipamentoTest {
    @Mock private CategoriaEquipamentoRepository categoriaRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @InjectMocks private ListarCategoriasEquipamento listar;

    @Test
    void deveInformarQuandoCategoriaNaoExistir() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException exception = assertThrows(EntidadeNaoEncontradaException.class,
                () -> listar.listar(99L, 1L));

        assertEquals("exception.categoriaEquipamento.notFound", exception.getMessage());
    }
}
