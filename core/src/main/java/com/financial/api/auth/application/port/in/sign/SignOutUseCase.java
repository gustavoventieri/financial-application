package com.financial.api.auth.application.port.in.sign;

public interface SignOutUseCase {
    void execute(String refreshToken);
}
