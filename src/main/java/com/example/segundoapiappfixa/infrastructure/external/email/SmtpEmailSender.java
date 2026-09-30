package com.example.segundoapiappfixa.infrastructure.external.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.nio.charset.StandardCharsets;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@RequiredArgsConstructor
public class SmtpEmailSender implements EmailSender {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username:}")
    private String senderAddress;

    @Override
    public void enviarCodigo(String recipient, String nome, String codigo, String validade) {

        try {
            Context context = new Context();

            context.setVariable("nome", nome);
            context.setVariable("codigo", codigo);
            context.setVariable("validade", validade);

            String html = templateEngine.process("email/recuperar-senha", context);

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

}
