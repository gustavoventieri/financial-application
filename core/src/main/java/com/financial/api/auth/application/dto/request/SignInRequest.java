package com.financial.api.auth.application.dto.request;

public record SignInRequest(
    String email,
    String password
) {
}
