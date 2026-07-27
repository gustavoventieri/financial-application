package com.financial.api.shared.email;

public interface EmailTemplateUseCase {
    String buildVerificationEmail(String otpCode);
}
