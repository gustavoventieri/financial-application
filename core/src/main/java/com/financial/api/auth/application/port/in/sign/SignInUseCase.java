package com.financial.api.auth.application.port.in.sign;

import com.financial.api.auth.application.dto.response.AuthResponse;

public interface SignInUseCase {
    AuthResponse execute(String email, String password, String ip, String device);
}
