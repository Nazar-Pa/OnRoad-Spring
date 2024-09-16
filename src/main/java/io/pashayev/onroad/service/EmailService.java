package io.pashayev.onroad.service;

public interface EmailService {
    void sendSimpleMailMessage(String name, String to, String token);

    void sendOTPtoNumber(String otp);
}
