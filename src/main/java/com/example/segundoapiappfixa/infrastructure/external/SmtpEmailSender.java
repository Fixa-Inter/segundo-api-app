package com.example.segundoapiappfixa.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.nio.charset.StandardCharsets;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class SmtpEmailSender implements EmailSender {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String senderAddress;

    @Override
    public void enviarCodigo(String recipient, String nome, String codigo, String validade) {

        try {
            ClassPathResource templateResource = new ClassPathResource("templates/email/recuperar-senha.html");

            String template;
            try (InputStream input = templateResource.getInputStream()) {
                template = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            }

            String html = template
                    .replace("__NOME__", escapeHtml(nome))
                    .replace("__CODIGO__", escapeHtml(codigo))
                    .replace("__VALIDADE__", escapeHtml(validade));

            MimeMessageHelper helper = new MimeMessageHelper(
                    mailSender.createMimeMessage(),
                    false,
                    StandardCharsets.UTF_8.name()
            );

            helper.setTo(recipient);
            if (senderAddress != null && !senderAddress.isBlank()) {
                helper.setFrom(senderAddress);
            }
            helper.setSubject("Código de Validação - Redefinição de Senha");
            helper.setText(html, true);

            mailSender.send(helper.getMimeMessage());
        } catch (Exception exception) {
            throw new IllegalStateException("Não foi possível enviar o e-mail de recuperação.", exception);
        }
    }

    private String escapeHtml(String value) {
        if (value == null) return "";
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
