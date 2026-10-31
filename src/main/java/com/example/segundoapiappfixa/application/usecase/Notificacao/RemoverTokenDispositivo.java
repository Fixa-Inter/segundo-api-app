package com.example.segundoapiappfixa.application.usecase.Notificacao;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.repository.TokenDispositivoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RemoverTokenDispositivo {

    private final TokenDispositivoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public void remover(Long usuarioId, String token) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        repository.findByToken(token).ifPresent(tokenExistente -> {
            if (!usuarioId.equals(tokenExistente.getUsuarioId())) {
                throw new RegraProblemaException("exception.access.denied");
            }
            repository.remover(token);
        });
    }

}
