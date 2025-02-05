package com.telecentro.api.infra.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    // TODO: Informar quando não encontrar o email
    public void sendMail(String studentEmail, String url) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(studentEmail);
        helper.setSubject("Confirme sua presença!");
        helper.setText(buildMessage(url), true);

        mailSender.send(message);
    }

    private String buildMessage(String url) {
        return """
                <html>
                    <body>
                        <h1>Olá, tudo bem?</h1>
                        <p>Estamos te enviando esse e-mail para confirmar sua presença.</p>
                        <a href="%s">Confirmar presença</a>
                        <p>Atenciosamente, equipe Telecentro.</p>
                    </body>
                </html>
                """.formatted(url);
    }
}
