package com.financial.api.auth.application.dto.response;

import lombok.Builder;

@Builder(toBuilder = true)
public record AuthResponse(
    String accessToken,
    String refreshToken
) {
}
