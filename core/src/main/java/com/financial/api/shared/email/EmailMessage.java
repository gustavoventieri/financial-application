package com.financial.api.shared.email;

public record EmailMessage(
        String recipient,
        String subject,
        String html
) {}