package com.ptit.a2.movie_theater_managent.service;

public interface EmailService {
  void sendOtpEmail(String recipientEmail, String otp);

  void sendEmailWithAttachment(
        String toEmail,
        String subject,
        String body,
        String attachmentFileName,
        byte[] attachmentData,
        String attachmentContentType);

  void sendLoginLinkEmail(String email, String loginLink);
}
