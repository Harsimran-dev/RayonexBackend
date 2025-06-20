package com.healthbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

import java.io.IOException;

@Service
public class EmailSendDocImpl implements EmailSendDoc {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendCalendarInvite(String toEmail, String subject, String body, String icsContent) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);

            ByteArrayDataSource dataSource = new ByteArrayDataSource(icsContent, "text/calendar; charset=utf-8");
            helper.addAttachment("appointment.ics", dataSource);

            javaMailSender.send(message);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    @Async
@Override
public void sendPensionFile(String toEmail, String subject, MultipartFile[] files) {
    MimeMessage message = javaMailSender.createMimeMessage();
    try {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText("Hi Thanks for registering to Pension Plan , You can see the benefits below");

        for (MultipartFile file : files) {
            try {
                ByteArrayDataSource dataSource = new ByteArrayDataSource(file.getBytes(), file.getContentType());
                helper.addAttachment(file.getOriginalFilename(), dataSource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        javaMailSender.send(message);
    } catch (MessagingException e) {
        e.printStackTrace();
    }
}

}
