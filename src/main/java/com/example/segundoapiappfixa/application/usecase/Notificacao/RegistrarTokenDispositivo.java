package com.example.segundoapiappfixa.application.usecase.Notificacao;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.TokenDispositivo;
import com.example.segundoapiappfixa.domain.repository.TokenDispositivoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UseCase
@RequiredArgsConstructor
public class RegistrarTokenDispositivo {
    private static final Logger log = LoggerFactory.getLogger(RegistrarTokenDispositivo.class);
    private final TokenDispositivoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public void registrar(Long usuarioId, String token) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        repository.findByToken(token).ifPresent(tokenExistente -> {
            if (!usuarioId.equals(tokenExistente.getUsuarioId())) {
                throw new RegraProblemaException("exception.access.denied");
            }
        });

        repository.adicionar(new TokenDispositivo(usuarioId, token));
        log.info("Token FCM registrado para usuário {}.", usuarioId);
    }
}
