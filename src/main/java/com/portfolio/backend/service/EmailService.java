package com.portfolio.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("harikasina50@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Your Verification Code (OTP)");
        message.setText("Hello,\n\nYour OTP for contacting K Venkat Chowdary is: " + otp + "\n\nThis code is valid for 10 minutes.");
        mailSender.send(message);
    }

    @Async
    public void sendNoReplyConfirmation(String toEmail, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("harikasina50@gmail.com");
        message.setTo(toEmail);
        message.setSubject("NO-REPLY: Message Received");
        message.setText("Dear " + name + ",\n\nThank you for reaching out! Your response has been recorded. I will get back to you shortly.\n\n*** This is an automated no-reply notification. ***");
        mailSender.send(message);
    }
}