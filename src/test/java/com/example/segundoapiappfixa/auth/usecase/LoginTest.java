package com.example.segundoapiappfixa.auth.usecase;

import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import com.example.segundoapiappfixa.application.exception.UsuarioInativoException;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.model.auth.IdentidadeGoogle;
import com.example.segundoapiappfixa.domain.model.auth.TokensAutenticacao;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.service.auth.AutenticadorCredenciais;
import com.example.segundoapiappfixa.domain.service.auth.VerificadorIdentidadeGoogle;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginTest {
    @Test
    void loginNormalAutenticaEEmiteTokensParaUsuarioAtivo() {
        var autenticador = mock(AutenticadorCredenciais.class);
        var google = mock(VerificadorIdentidadeGoogle.class);
        var usuarios = mock(UsuarioRepository.class);
        var sessao = mock(GerenciarSessao.class);
        var usuario = new Usuario(); usuario.setEstaAtivo(true); usuario.setEmail("pessoa@exemplo.com");
        when(autenticador.autenticar("pessoa@exemplo.com", "senha")).thenReturn("pessoa@exemplo.com");
        when(usuarios.findByEmail("pessoa@exemplo.com")).thenReturn(Optional.of(usuario));
        when(sessao.criarSessao(usuario)).thenReturn(new TokensAutenticacao("access", "refresh"));
        var resultado = new Login(autenticador, google, usuarios, sessao).login("pessoa@exemplo.com", "senha");
        assertEquals("access", resultado.tokens().accessToken());
        assertEquals("refresh", resultado.tokens().refreshToken());
    }

    @Test
    void googleSemCadastroSolicitaRegistroESemVerificacaoRecusa() {
        var google = mock(VerificadorIdentidadeGoogle.class);
        var usuarios = mock(UsuarioRepository.class);
        var sessao = mock(GerenciarSessao.class);
        var login = new Login(mock(AutenticadorCredenciais.class), google, usuarios, sessao);
        when(google.verificar("valido")).thenReturn(new IdentidadeGoogle(" PESSOA@EXEMPLO.COM ", "Pessoa", true));
        when(usuarios.findByEmail("pessoa@exemplo.com")).thenReturn(Optional.empty());
        var resultado = login.loginGoogle("valido");
        assertEquals("REGISTRATION_REQUIRED", resultado.status());
        assertEquals("pessoa@exemplo.com", resultado.email());
        assertNull(resultado.tokens());
        when(google.verificar("nao-verificado")).thenReturn(new IdentidadeGoogle("a@b.com", "A", false));
        assertThrows(RegraProblemaException.class, () -> login.loginGoogle("nao-verificado"));
        verifyNoInteractions(sessao);
    }

    @Test
    void loginNaoEmiteSessaoParaUsuarioInativo() {
        var autenticador = mock(AutenticadorCredenciais.class);
        var usuarios = mock(UsuarioRepository.class);
        var sessao = mock(GerenciarSessao.class);
        var usuario = new Usuario(); usuario.setEstaAtivo(false);
        when(usuarios.findByEmail("a@b.com")).thenReturn(Optional.of(usuario));
        var login = new Login(autenticador, mock(VerificadorIdentidadeGoogle.class), usuarios, sessao);
        assertThrows(UsuarioInativoException.class, () -> login.login("a@b.com", "senha"));
        verifyNoInteractions(sessao);
    }

    @Test
    void googleComContaAtivaRetornaTokensDaSessao() {
        var google = mock(VerificadorIdentidadeGoogle.class);
        var usuarios = mock(UsuarioRepository.class);
        var sessao = mock(GerenciarSessao.class);
        var usuario = new Usuario(); usuario.setEstaAtivo(true);
        usuario.setEmail("pessoa@exemplo.com"); usuario.setNomeCompleto("Pessoa");
        when(google.verificar("google")).thenReturn(new IdentidadeGoogle("pessoa@exemplo.com", "Pessoa", true));
        when(usuarios.findByEmail("pessoa@exemplo.com")).thenReturn(Optional.of(usuario));
        when(sessao.criarSessao(usuario)).thenReturn(new TokensAutenticacao("access", "refresh"));
        var resultado = new Login(mock(AutenticadorCredenciais.class), google, usuarios, sessao).loginGoogle("google");
        assertEquals("AUTHENTICATED", resultado.status());
        assertEquals("access", resultado.tokens().accessToken());
        assertEquals("refresh", resultado.tokens().refreshToken());
    }
}
