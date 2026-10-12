package com.example.segundoapiappfixa.application.usecase.Perfil;

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
class DetalhesPerfilTest {
    @Mock private UsuarioRepository usuarioRepository;
    @InjectMocks private DetalhesPerfil detalhesPerfil;

    @Test
    void deveRetornarErroQuandoPerfilNaoExistir() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException exception = assertThrows(EntidadeNaoEncontradaException.class,
                () -> detalhesPerfil.detalhesPerfil(99L));

        assertEquals("exception.usuario.required", exception.getMessage());
    }
}
