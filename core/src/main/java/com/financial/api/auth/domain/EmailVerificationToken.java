package com.financial.api.auth.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record EmailVerificationToken(
        Long id,
        String userId,
        String otpCode,
        LocalDateTime expiresAt,
        LocalDateTime createdAt,
        LocalDateTime usedAt
) {
    public boolean isExpired() {
        return expiresAt.isBefore(LocalDateTime.now());
    }

    public boolean isUsed() {
        return usedAt != null;
    }

    public EmailVerificationToken markAsUsed() {
        return new EmailVerificationToken(
                id,
                userId,
                otpCode,
                expiresAt,
                createdAt,
                LocalDateTime.now()
        );
    }
}
