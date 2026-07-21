package com.financial.api.auth.domain;

import java.time.LocalDateTime;

public record RefreshToken(
        String id,
        String userId,
        String tokenHash,
        LocalDateTime expiresAt,
        LocalDateTime createdAt,
        LocalDateTime revokedAt
) {
}
