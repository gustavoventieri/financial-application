package com.financial.api.auth.application.port.in.accessToken;

public interface ExtractUserIdFromAccessTokenUseCase {
    String execute(String token);
}

