package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender mailSender;
  private static final String MESSAGE_SUBJECT = "Your OTP Code";
  private static final String MESSAGE_BODY = "Your OTP code is: %s. It is valid for 5 minutes.";
  private static final String fromEmail = "nguyennanhtu143@gmail.com";

  @Override
  public void sendOtpEmail(String recipientEmail, String otp) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(recipientEmail);
    message.setSubject(MESSAGE_SUBJECT);
    message.setText(String.format(MESSAGE_BODY, otp));
    mailSender.send(message);
  }

  @Override
  public void sendEmailWithAttachment(
        String toEmail,
        String subject,
        String body,
        String attachmentFileName,
        byte[] attachmentData,
        String attachmentContentType) {

    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setFrom(fromEmail);
      helper.setTo(toEmail);
      helper.setSubject(subject);
      helper.setText(body);

      // Tạo file đính kèm
      ByteArrayDataSource dataSource = new ByteArrayDataSource(
            attachmentData,
            attachmentContentType
      );
      helper.addAttachment(attachmentFileName, dataSource);

      mailSender.send(message);
      log.info("Email sent successfully to: {}", toEmail);

    } catch (Exception e) {
      log.error("Failed to send email to: {}", toEmail, e);
      throw new RuntimeException("Failed to send email", e);
    }
  }

  @Override
  public void sendLoginLinkEmail(String email, String loginLink) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setSubject("Login Link - Personal Financial Management");
    message.setText(
          "Hello,\n\n" +
                "You have requested to login to your account. Click the link below to login:\n\n" +
                loginLink + "\n\n" +
                "This link will expire in 10 minutes.\n\n" +
                "If you did not request this login link, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Personal Financial Management Team"
    );

    mailSender.send(message);
    log.info("Login link email sent to: {}", email);
  }
}
