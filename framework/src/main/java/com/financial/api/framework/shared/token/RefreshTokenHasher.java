package com.financial.api.framework.shared.token;

import com.financial.api.auth.application.port.in.refresh.HashRefreshTokenUseCase;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenHasher implements HashRefreshTokenUseCase {
    @Override
    public String execute(String refreshToken) {
        return "";
    }
}
