package com.example.segundoapiappfixa.infrastructure.security;

import com.example.segundoapiappfixa.infrastructure.config.FirebaseProperties;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class FirebaseTokenVerifier {

    private final FirebaseProperties properties;

    public FirebaseTokenVerifier(FirebaseProperties properties) {
        this.properties = properties;
    }

    public FirebaseToken verify(String idToken) {
        if (idToken == null || idToken.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "ID Token do Google/Firebase não informado"
            );
        }

        try {
            return FirebaseAuth.getInstance(getApp()).verifyIdToken(idToken);
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

    private FirebaseApp getApp() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.getInstance();
        }

        if (properties.credentialsJson() != null && !properties.credentialsJson().isBlank()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(
                            new ByteArrayInputStream(properties.credentialsJson().getBytes(StandardCharsets.UTF_8))))
                    .build();
            return FirebaseApp.initializeApp(options);
        }

        if (properties.credentialsPath() == null || properties.credentialsPath().isBlank()) {
            throw new IllegalStateException(
                    "Configure firebase.credentials-path ou firebase.credentials-json"
            );
        }

        try (FileInputStream credentials = new FileInputStream(properties.credentialsPath())) {
            FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.fromStream(credentials))
                    .build();

            return FirebaseApp.initializeApp(options);
        }
    }
}
