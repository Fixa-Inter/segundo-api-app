package com.example.segundoapiappfixa.auth.usecase;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.auth.TokensAutenticacao;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RenovarToken {
    private final GerenciarSessao sessao;
    public TokensAutenticacao renovar(String refreshToken) { return sessao.renovar(refreshToken); }
}
