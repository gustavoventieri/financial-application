package com.financial.api.auth.application.port.in.refresh;

public interface HashRefreshTokenUseCase {
    String execute(String refreshToken);
}
