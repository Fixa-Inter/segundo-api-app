package com.example.segundoapiappfixa.infrastructure.security;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.example.segundoapiappfixa.domain.model.auth.IdentidadeGoogle;
import com.example.segundoapiappfixa.domain.service.auth.VerificadorIdentidadeGoogle;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
public class FirebaseTokenVerifier implements VerificadorIdentidadeGoogle {

    private final FirebaseAdminAppProvider firebaseAdminAppProvider;

    public FirebaseTokenVerifier(FirebaseAdminAppProvider firebaseAdminAppProvider) {
        this.firebaseAdminAppProvider = firebaseAdminAppProvider;
    }

    @Override
    public IdentidadeGoogle verificar(String idToken) {

        if (idToken == null || idToken.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "ID Token do Google/Firebase não informado"
            );
        }

        try {
            FirebaseApp app = firebaseAdminAppProvider.getApp();
            FirebaseToken token = FirebaseAuth.getInstance(app).verifyIdToken(idToken);

            return new IdentidadeGoogle(
                    token.getEmail(),
                    token.getName(),
                    Boolean.TRUE.equals(token.isEmailVerified())
            );

        } catch (FirebaseAuthException exception) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "ID Token do Google/Firebase inválido: " + exception.getAuthErrorCode(),
                    exception
            );

        } catch (IOException exception) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "ID Token do Google/Firebase inválido",
                    exception
            );
        }
    }

}
