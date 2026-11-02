package com.example.segundoapiappfixa.auth.service;

import com.example.segundoapiappfixa.auth.dto.IssuedTokens;
import com.example.segundoapiappfixa.domain.model.RefreshToken;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.RefreshTokenRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.application.exception.RefreshTokenInvalidoException;
import com.example.segundoapiappfixa.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenSessionService {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final ConcurrentMap<String, TokenLock> refreshTokenLocks = new ConcurrentHashMap<>();

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UsuarioRepository usuarioRepository;

    public IssuedTokens criarSessao(Usuario usuario) {
        return emitirPar(usuario, UUID.randomUUID().toString());
    }

    public IssuedTokens renovar(String tokenOriginal) {
        if (tokenOriginal == null || tokenOriginal.isBlank()) {
            throw new RefreshTokenInvalidoException();
        }

        String hash = hash(tokenOriginal);
        TokenLock lock = adquirirLock(hash);

        try {
            return renovarComLock(hash);
        } finally {
            liberarLock(hash, lock);
        }
    }

    private IssuedTokens renovarComLock(String hash) {
        RefreshToken tokenAtual = refreshTokenRepository.buscarPorHash(hash)
                .orElseThrow(RefreshTokenInvalidoException::new);

        if (!tokenAtual.isAtivo()) {
            refreshTokenRepository.revogarFamilia(tokenAtual.getFamiliaId());
            throw new RefreshTokenInvalidoException();
        }

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(tokenAtual.getUsuarioId());
        Usuario usuario = usuarioEncontrado.orElseThrow(RefreshTokenInvalidoException::new);

        if (!Boolean.TRUE.equals(usuario.getEstaAtivo())) {
            refreshTokenRepository.revogarFamilia(tokenAtual.getFamiliaId());
            throw new DisabledException("Usuário inativo");
        }

        refreshTokenRepository.consumir(tokenAtual);

        return emitirPar(usuario, tokenAtual.getFamiliaId());
    }

    public void revogar(String token) {
        if (token == null || token.isBlank()) return;

        String hash = hash(token);
        TokenLock lock = adquirirLock(hash);
        try {
            refreshTokenRepository.buscarPorHash(hash)
                    .ifPresent(refreshToken -> refreshTokenRepository.revogarFamilia(refreshToken.getFamiliaId()));
        } finally {
            liberarLock(hash, lock);
        }
    }

    private TokenLock adquirirLock(String tokenHash) {
        TokenLock lock = refreshTokenLocks.compute(tokenHash, (key, current) -> {
            TokenLock selected = current == null ? new TokenLock() : current;
            selected.referencias++;
            return selected;
        });

        lock.lock.lock();
        return lock;
    }

    private void liberarLock(String tokenHash, TokenLock lock) {
        lock.lock.unlock();
        refreshTokenLocks.compute(tokenHash, (key, current) -> {
            if (current != lock) return current;
            lock.referencias--;
            return lock.referencias == 0 ? null : lock;
        });
    }

    private IssuedTokens emitirPar(Usuario usuario, String familiaId) {
        String refreshToken = gerarRefreshToken();
        RefreshToken registro = new RefreshToken(
                hash(refreshToken),
                usuario.getId(),
                familiaId,
                true,
                LocalDateTime.now()
        );

        String accessToken = jwtTokenProvider.generateAccessToken(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getTipoAcesso().name(),
                new Date()
        );

        refreshTokenRepository.salvar(registro);
        return new IssuedTokens(accessToken, refreshToken);
    }

    private String gerarRefreshToken() {
        byte[] bytes = new byte[32];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hash(String token) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(token.getBytes(StandardCharsets.UTF_8));

            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (Exception exception) {
            throw new IllegalStateException("Não foi possível proteger o refresh token.", exception);
        }
    }

    private static final class TokenLock {
        private final ReentrantLock lock = new ReentrantLock();
        private int referencias;
    }
}
