package com.healthbackend.services;

import org.springframework.web.multipart.MultipartFile;



public interface EmailSendDoc {
 
    void sendEmail(String toEmail, String subject, String body);
    void sendPensionFile(String toEmail, String subject, MultipartFile[] files); // ✅ Add this
    void sendCalendarInvite(String toEmail, String subject, String body, String icsContent);

}
