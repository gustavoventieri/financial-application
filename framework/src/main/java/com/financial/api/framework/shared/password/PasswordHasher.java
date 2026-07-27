package com.financial.api.framework.shared.password;

import com.financial.api.auth.application.port.in.password.HashPasswordUseCase;
import org.springframework.stereotype.Component;

import com.financial.api.auth.application.port.in.password.HashPasswordUseCase;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher implements HashPasswordUseCase {

    private final BCryptProvider bcryptProvider;

    public PasswordHasher(BCryptProvider bcryptProvider) {
        this.bcryptProvider = bcryptProvider;
    }

    @Override
    public String execute(String password) {
        return bcryptProvider.encode(password);
    }
}