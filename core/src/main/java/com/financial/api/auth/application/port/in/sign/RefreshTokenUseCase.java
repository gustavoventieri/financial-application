package com.financial.api.auth.application.port.in.sign;

import com.financial.api.auth.application.dto.response.AuthResponse;

public interface RefreshTokenUseCase {
    AuthResponse execute(String refreshToken);

}
