package com.financial.api.auth.application.port.in.sign;

import com.financial.api.auth.application.dto.response.AuthResponse;

public interface VerifyEmailVerificationTokenUseCase {
    AuthResponse execute(
            String codeOtp,
            String email,
            String ip,
            String device
    );
}
