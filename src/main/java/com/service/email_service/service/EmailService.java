package com.service.email_service.service;

public interface EmailService {
    void sendEmail(String to, String subject, String message);
}
