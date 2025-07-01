package co.gestion.canchas.reservas.autenticacion.service.impl;

import co.gestion.canchas.reservas.autenticacion.dto.CorreoDto;
import co.gestion.canchas.reservas.autenticacion.service.IEnviarCorreoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnviarCorreoService implements IEnviarCorreoService {


    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;


    @Override
    @Async
    public void enviarCorreo(CorreoDto correo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(correo.getTo());
        message.setSubject(correo.getSubject());
        message.setText(correo.getText());
        mailSender.send(message);
    }
}
