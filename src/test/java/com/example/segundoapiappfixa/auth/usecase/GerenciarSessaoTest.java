package com.example.segundoapiappfixa.auth.usecase;

import com.example.segundoapiappfixa.application.exception.RefreshTokenInvalidoException;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.RefreshToken;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.RefreshTokenRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.service.auth.EmissorAccessToken;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GerenciarSessaoTest {
    @Test
    void renovacaoConsomeTokenAnteriorERotacionaNaMesmaFamilia() {
        var emissor = mock(EmissorAccessToken.class);
        var tokens = mock(RefreshTokenRepository.class);
        var usuarios = mock(UsuarioRepository.class);
        var usuario = new Usuario(); usuario.setId(1L); usuario.setEmail("a@b.com");
        usuario.setTipoAcesso(TipoAcesso.TECNICO); usuario.setEstaAtivo(true);
        when(emissor.generateAccessToken(eq(1L), eq("a@b.com"), eq("TECNICO"), any())).thenReturn("access");
        var sessao = new GerenciarSessao(emissor, tokens, usuarios);
        var primeiro = sessao.criarSessao(usuario);
        ArgumentCaptor<RefreshToken> captor = ArgumentCaptor.forClass(RefreshToken.class);
        verify(tokens).salvar(captor.capture());
        RefreshToken anterior = captor.getValue();
        assertNotEquals(primeiro.refreshToken(), anterior.getTokenHash());
        when(tokens.buscarPorHash(anterior.getTokenHash())).thenReturn(Optional.of(anterior));
        when(usuarios.findById(1L)).thenReturn(Optional.of(usuario));

        var renovado = sessao.renovar(primeiro.refreshToken());
        assertNotEquals(primeiro.refreshToken(), renovado.refreshToken());
        verify(tokens).consumir(anterior);
        verify(tokens, times(2)).salvar(captor.capture());
        RefreshToken novo = captor.getAllValues().getLast();
        assertEquals(anterior.getFamiliaId(), novo.getFamiliaId());
        assertNotEquals(anterior.getTokenHash(), novo.getTokenHash());
    }

    @Test
    void tokenRevogadoRevogaFamiliaERecusaRenovacao() {
        var emissor = mock(EmissorAccessToken.class);
        var tokens = mock(RefreshTokenRepository.class);
        var usuarios = mock(UsuarioRepository.class);
        var anterior = new RefreshToken("hash", 1L, "familia", false, null);
        when(tokens.buscarPorHash(anyString())).thenReturn(Optional.of(anterior));
        var sessao = new GerenciarSessao(emissor, tokens, usuarios);
        assertThrows(RefreshTokenInvalidoException.class, () -> sessao.renovar("refresh"));
        verify(tokens).revogarFamilia("familia");
        verifyNoInteractions(emissor, usuarios);
    }
}
