package com.financial.api.shared.email;

public interface EmailTemplateUseCase {
    String buildVerificationEmail(String otpCode);

    String buildAccountCreatedEmail(
            String name,
            String otpCode
    );

    String buildNewLoginEmail(
            String name,
            String device,
            String ip,
            String dateTime
    );
}
