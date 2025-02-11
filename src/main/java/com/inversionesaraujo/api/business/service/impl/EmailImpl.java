package com.inversionesaraujo.api.business.service.impl;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.business.request.EmailRequest;
import com.inversionesaraujo.api.business.service.IEmail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailImpl implements IEmail {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailImpl.class);
    
    @Autowired
    private JavaMailSender sender;

    @Override
    public boolean sendEmail(EmailRequest body) {
        return sendEmailTool(body.getContent(), body.getDestination(), body.getSubject());
    }
    
    private boolean sendEmailTool(String textMessage, String email, String subject) {
        boolean send = false;
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(email);
            helper.setText(textMessage, true);
            helper.setSubject(subject);
            helper.setFrom("i2115610@continental.edu.pe", "Inversiones Araujo");
            sender.send(message);
            send = true;
            LOGGER.info("El email se envio con exito");
        }catch(MessagingException | UnsupportedEncodingException error) {
            LOGGER.error("Hubo un error al enviar el email", error);
        }

        return send;
    }
}
