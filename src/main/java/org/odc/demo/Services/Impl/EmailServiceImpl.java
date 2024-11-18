package org.odc.demo.Services.Impl;

import jakarta.mail.internet.MimeMessage;
import org.odc.demo.Services.Interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Async
    @Override
    public void sendAuthenticationEmail(String email, String login, String password, byte[] qrCodeCard) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // 'true' for multipart

            // Set email content
            helper.setFrom("mousdef34@gmail.com");
            helper.setTo(email);
            helper.setSubject("Your Login Credentials and QR Code");

            // Use HTML for the email body
            String emailBody = "<html><body>" +
                    "<p>Login: " + login + "</p>" +
                    "<p>Password: " + password + "</p>" +
                    "<p>Please change your password after login.</p>" +
                    "<p>Your QR code is attached below:</p>" +
                    "<img src='cid:QRCodeCard.png' alt='QR Code' />" +
                    "</body></html>";
            helper.setText(emailBody, true);  // 'true' for HTML content

            // Convert byte[] to ByteArrayResource (InputStreamSource)
            ByteArrayResource qrCodeResource = new ByteArrayResource(qrCodeCard);

            // Attach the QR code image as an inline file (using the ByteArrayResource)
            helper.addInline("QRCodeCard.png", qrCodeResource, "image/png");

            // Send the email
            mailSender.send(message);

        } catch (jakarta.mail.MessagingException e) {
            logger.error("Error sending email to {}", email, e);
            throw new RuntimeException("Error sending email", e);
        }
    }
}
