package com.example.segundoapiappfixa.infrastructure.security;

import com.example.segundoapiappfixa.infrastructure.config.FirebaseProperties;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class FirebaseAdminAppProvider {
    private final FirebaseProperties properties;

    public synchronized FirebaseApp getApp() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) return FirebaseApp.getInstance();

        if (properties.credentialsJson() != null && !properties.credentialsJson().isBlank()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setProjectId(properties.projectId())
                    .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(
                            properties.credentialsJson().getBytes(StandardCharsets.UTF_8))))
                    .build();
            return FirebaseApp.initializeApp(options);
        }

        if (properties.credentialsPath() == null || properties.credentialsPath().isBlank()) {
            throw new IllegalStateException("Configure firebase.credentials-path ou firebase.credentials-json");
        }
        try (FileInputStream credentials = new FileInputStream(properties.credentialsPath())) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setProjectId(properties.projectId())
                    .setCredentials(GoogleCredentials.fromStream(credentials))
                    .build();
            return FirebaseApp.initializeApp(options);
        }
    }
}
