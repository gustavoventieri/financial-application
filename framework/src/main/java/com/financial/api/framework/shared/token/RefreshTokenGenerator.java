package com.financial.api.framework.shared.token;

import com.financial.api.auth.application.port.in.refresh.GenerateRefreshTokenUseCase;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class RefreshTokenGenerator implements GenerateRefreshTokenUseCase {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();

    @Override
    public String execute() {

        byte[] bytes = new byte[32];

        SECURE_RANDOM.nextBytes(bytes);

        return ENCODER.encodeToString(bytes);
    }
}