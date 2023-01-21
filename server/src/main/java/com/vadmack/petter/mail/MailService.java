package com.vadmack.petter.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailService {

    private final MailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void send(String recipientAddress, String subject, String text) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setFrom(senderEmail);
        email.setText(text);
        mailSender.send(email);
    }
}
