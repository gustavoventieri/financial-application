package com.financial.api.auth.application.dto.response;

public record AuthResponse(
    String accessToken,
    String refreshToken
) {
}
