package org.odc.demo.Services.Interfaces;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    @Async
    void sendAuthenticationEmail(String email, String login, String password, byte[] qrCodeCard);
}
