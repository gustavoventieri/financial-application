package com.financial.api.framework.auth.utils.password;

import com.financial.api.auth.application.port.in.password.HashPasswordUseCase;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryption implements HashPasswordUseCase {

    private final BCryptProvider bcryptProvider;

    public PasswordEncryption(BCryptProvider bcryptProvider) {
        this.bcryptProvider = bcryptProvider;
    }

    @Override
    public String execute(String password) {
        return bcryptProvider.encode(password);
    }
}