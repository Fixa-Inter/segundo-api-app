package com.example.segundoapiappfixa.auth.usecase;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class Logout {
    private final GerenciarSessao sessao;
    public void executar(String refreshToken) { sessao.revogar(refreshToken); }
}
