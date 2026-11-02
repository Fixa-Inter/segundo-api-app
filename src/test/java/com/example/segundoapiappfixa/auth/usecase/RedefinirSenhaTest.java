package com.example.segundoapiappfixa.auth.usecase;

import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.RecuperarSenhaCodigoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.service.auth.CodificadorSenha;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedefinirSenhaTest {
    @Test
    void codigoValidoSalvaSenhaCodificadaEExcluiCodigo() {
        var usuarios = mock(UsuarioRepository.class);
        var codigos = mock(RecuperarSenhaCodigoRepository.class);
        var codificador = mock(CodificadorSenha.class);
        var recuperacao = mock(EsqueciSenha.class);
        var usuario = new Usuario(); usuario.setId(8L);
        when(usuarios.findByEmail("a@b.com")).thenReturn(Optional.of(usuario));
        when(recuperacao.validarCodigo("a@b.com", 123456)).thenReturn(true);
        when(codificador.codificar("nova")).thenReturn("hash");
        var redefinir = new RedefinirSenha(usuarios, codigos, codificador, recuperacao);
        assertEquals("Nova senha registrada com sucesso!", redefinir.redefinir("a@b.com", 123456, "nova"));
        assertEquals("hash", usuario.getSenhaHash());
        verify(usuarios).save(usuario);
        verify(codigos).deleteAll(8L);
    }

    @Test
    void codigoInvalidoNaoMudaSenha() {
        var usuarios = mock(UsuarioRepository.class);
        var codigos = mock(RecuperarSenhaCodigoRepository.class);
        var codificador = mock(CodificadorSenha.class);
        var recuperacao = mock(EsqueciSenha.class);
        when(usuarios.findByEmail("a@b.com")).thenReturn(Optional.of(new Usuario()));
        var redefinir = new RedefinirSenha(usuarios, codigos, codificador, recuperacao);
        assertThrows(RegraProblemaException.class, () -> redefinir.redefinir("a@b.com", 123456, "nova"));
        verify(usuarios, never()).save(any());
        verifyNoInteractions(codigos, codificador);
    }
}
