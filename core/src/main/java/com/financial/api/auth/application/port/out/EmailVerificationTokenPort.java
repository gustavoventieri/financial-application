package com.financial.api.auth.application.port.out;

import com.financial.api.auth.domain.EmailVerificationToken;

import java.util.Optional;

public interface EmailVerificationTokenPort {
    void save(EmailVerificationToken emailVerificationToken);
    Optional<EmailVerificationToken> findByUserId(String email);
}
