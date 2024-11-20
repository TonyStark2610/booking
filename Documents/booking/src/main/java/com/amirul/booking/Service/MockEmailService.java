package com.amirul.booking.Service;

import org.springframework.stereotype.Service;

@Service
public class MockEmailService {
    public boolean sendVerifyEmail(String email) {
        System.out.println("Mock: Sending verification email to " + email);
        return true;
    }
}
