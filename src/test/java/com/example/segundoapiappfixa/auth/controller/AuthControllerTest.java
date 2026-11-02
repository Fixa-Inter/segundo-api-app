package com.example.segundoapiappfixa.auth.controller;

import com.example.segundoapiappfixa.auth.dto.GoogleLoginRequestDTO;
import com.example.segundoapiappfixa.auth.dto.LoginRequestDTO;
import com.example.segundoapiappfixa.auth.usecase.*;
import com.example.segundoapiappfixa.domain.model.auth.LoginGoogleResultado;
import com.example.segundoapiappfixa.domain.model.auth.LoginResultado;
import com.example.segundoapiappfixa.domain.model.auth.TokensAutenticacao;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Test
    void preservaCorpoHttpDeLoginEGoogleSemCadastro() {
        var login = mock(Login.class);
        var controller = new AuthController(mock(Cadastrar.class), login, mock(RenovarToken.class),
                mock(Logout.class), mock(EsqueciSenha.class), mock(RedefinirSenha.class), mock(ListarOpcoesCadastro.class));
        when(login.login("pessoa@escola.com", "senha"))
                .thenReturn(new LoginResultado("pessoa@escola.com", new TokensAutenticacao("access", "refresh")));
        var resposta = controller.login(new LoginRequestDTO("pessoa@escola.com", "senha")).getBody();
        assertNotNull(resposta);
        assertEquals("pessoa@escola.com", resposta.email());
        assertEquals("access", resposta.token());
        assertEquals("refresh", resposta.refreshToken());

        when(login.loginGoogle("google")).thenReturn(new LoginGoogleResultado("REGISTRATION_REQUIRED", "pessoa@escola.com", "Pessoa", null));
        var google = controller.loginGoogle(new GoogleLoginRequestDTO("google")).getBody();
        assertNotNull(google);
        assertEquals("REGISTRATION_REQUIRED", google.status());
        assertNull(google.token());
        assertNull(google.refreshToken());
    }
}
