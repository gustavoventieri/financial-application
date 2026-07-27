package com.financial.api.auth.application.port.in.emailVerification;

public interface HashEmailVerificationTokenUseCase {
    String execute(String otpCode);
}
