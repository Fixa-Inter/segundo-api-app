package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.RecuperarSenhaCodigo;

public interface PasswordTokenRepository {

    void save(RecuperarSenhaCodigo passwordResetToken);
    RecuperarSenhaCodigo findActiveByUsuarioId(Long usuarioId);

}
