package com.financial.api.framework.shared.token;

import com.financial.api.auth.application.port.in.refresh.GenerateRefreshTokenUseCase;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenGenerator implements GenerateRefreshTokenUseCase {
    @Override
    public String execute() {
        return "";
    }
}
