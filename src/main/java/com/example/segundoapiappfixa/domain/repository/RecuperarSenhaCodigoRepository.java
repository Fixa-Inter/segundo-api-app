package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.RecuperarSenhaCodigo;

public interface RecuperarSenhaCodigoRepository {

    void save(RecuperarSenhaCodigo passwordResetToken);

    RecuperarSenhaCodigo findByUsuarioIdAndEstaAtivo(Long usuarioId);

    void deleteAll(Long usuarioId);

}
