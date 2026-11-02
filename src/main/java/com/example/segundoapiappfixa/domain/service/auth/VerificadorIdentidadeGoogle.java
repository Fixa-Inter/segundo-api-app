package com.example.segundoapiappfixa.domain.service.auth;

import com.example.segundoapiappfixa.domain.model.auth.IdentidadeGoogle;

public interface VerificadorIdentidadeGoogle {
    IdentidadeGoogle verificar(String idToken);
}
