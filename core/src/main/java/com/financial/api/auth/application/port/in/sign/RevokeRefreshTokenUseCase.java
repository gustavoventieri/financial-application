package com.financial.api.auth.application.port.in.sign;

public interface RevokeRefreshTokenUseCase {
    void execute(String refreshToken);
}

