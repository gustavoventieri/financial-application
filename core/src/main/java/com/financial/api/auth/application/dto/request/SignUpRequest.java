package com.financial.api.auth.application.dto.request;

public record SignUpRequest(
        String name,
        String email,
        String password
) {
}
