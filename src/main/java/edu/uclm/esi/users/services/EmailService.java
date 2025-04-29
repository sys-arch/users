package edu.uclm.esi.users.services;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

@Service
public class EmailService {

    private final Resend resend;

    // Inyecta la clave de API desde `application.properties`
    public EmailService(@Value("${resend.api.key}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

    public void enviarEmail(String email, String asunto, String mensaje) {
        CreateEmailOptions params = CreateEmailOptions.builder()
        		.from("QCode <qcode@updates.swey.net>")
                .to(email)
                .subject(asunto)
                .html(mensaje)
                .build();

        try {
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println("Correo enviado, ID de mensaje: " + data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
        }
    }

    public boolean validarEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        email = email.trim();
        return validator.isValid(email);
    }
}

