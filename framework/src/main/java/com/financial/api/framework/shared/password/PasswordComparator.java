package com.financial.api.framework.shared.password;

import com.financial.api.auth.application.port.in.password.ComparePasswordUseCase;
import org.springframework.stereotype.Component;

@Component
public class PasswordComparator implements ComparePasswordUseCase {

    private final BCryptProvider bcryptProvider;

    public PasswordComparator(BCryptProvider bcryptProvider) {
        this.bcryptProvider = bcryptProvider;
    }

    @Override
    public boolean execute(String rawPassword, String encodedPassword) {
        return bcryptProvider.matches(rawPassword, encodedPassword);
    }
}