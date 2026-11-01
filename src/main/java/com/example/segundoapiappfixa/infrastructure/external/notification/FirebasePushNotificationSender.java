package com.example.segundoapiappfixa.infrastructure.external.notification;

import com.example.segundoapiappfixa.domain.model.Notificacao;
import com.example.segundoapiappfixa.domain.repository.PushNotificationSender;
import com.example.segundoapiappfixa.domain.repository.TokenDispositivoRepository;
import com.example.segundoapiappfixa.infrastructure.security.FirebaseAdminAppProvider;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FirebasePushNotificationSender implements PushNotificationSender {
    private static final Logger log = LoggerFactory.getLogger(FirebasePushNotificationSender.class);
    private static final int MAX_TOKENS_PER_BATCH = 500;

    private final TokenDispositivoRepository tokenRepository;
    private final FirebaseAdminAppProvider firebaseAppProvider;

    @Override
    public void enviar(Notificacao notificacao) {
        Set<String> registeredTokens = tokenRepository.listarPorUsuario(notificacao.getUsuarioId());
        if (registeredTokens.isEmpty()) {
            log.warn("Push {} não enviado: nenhum token FCM registrado para usuário {}.",
                    notificacao.getId(), notificacao.getUsuarioId());
            return;
        }

        log.info("Enviando push {} para usuário {} em {} dispositivo(s).",
                notificacao.getId(), notificacao.getUsuarioId(), registeredTokens.size());

        List<String> tokens = new ArrayList<>(registeredTokens);
        for (int start = 0; start < tokens.size(); start += MAX_TOKENS_PER_BATCH) {
            List<String> batch = tokens.subList(
                    start,
                    Math.min(start + MAX_TOKENS_PER_BATCH, tokens.size())
            );

            try {
                MulticastMessage message = MulticastMessage.builder()
                        .addAllTokens(batch)
                        .setNotification(Notification.builder()
                                .setTitle("FIXA")
                                .setBody(notificacao.getMensagem()).build())
                        .putAllData(Map.of(
                                "notificacaoId", notificacao.getId(),
                                "tipoNotificacao", String.valueOf(notificacao.getTipoNotificacao()),
                                "referenciaId", notificacao.getReferenciaId() == null
                                        ? ""
                                        : String.valueOf(notificacao.getReferenciaId()
                                )
                        ))
                        .build();

                BatchResponse response = FirebaseMessaging
                        .getInstance(firebaseAppProvider.getApp())
                        .sendEachForMulticast(message);

                log.info("Resultado FCM da notificação {}: {} sucesso(s), {} falha(s).",
                        notificacao.getId(), response.getSuccessCount(), response.getFailureCount());

                for (int i = 0; i < response.getResponses().size(); i++) {
                    SendResponse send = response.getResponses().get(i);

                    if (!send.isSuccessful() && send.getException() != null) {
                        MessagingErrorCode code = send.getException().getMessagingErrorCode();

                        if (code == MessagingErrorCode.UNREGISTERED || code == MessagingErrorCode.INVALID_ARGUMENT) {
                            tokenRepository.findByToken(batch.get(i))
                                    .filter(token -> notificacao.getUsuarioId().equals(token.getUsuarioId()))
                                    .ifPresent(token -> tokenRepository.remover(token.getToken()));
                        } else {
                            log.warn("Falha FCM da notificação {} para usuário {} ({}): {}",
                                    notificacao.getId(), notificacao.getUsuarioId(), code,
                                    send.getException().getMessage());
                        }
                    }
                }

            } catch (Exception exception) {
                log.warn(
                        "Não foi possível enviar push da notificação {} para usuário {}.",
                        notificacao.getId(),
                        notificacao.getUsuarioId(),
                        exception
                );
            }
        }
    }
}
