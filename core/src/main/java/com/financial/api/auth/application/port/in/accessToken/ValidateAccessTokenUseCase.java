package com.financial.api.auth.application.port.in.accessToken;

public interface ValidateAccessTokenUseCase {
    boolean execute(String token);
}
