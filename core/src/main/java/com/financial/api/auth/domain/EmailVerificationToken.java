package com.financial.api.auth.domain;

import java.time.LocalDateTime;

public record EmailVerificationToken(
        String id,
        String userId,
        String tokenHash,
        LocalDateTime expiresAt,
        LocalDateTime createdAt,
        LocalDateTime usedAt
) {
}
