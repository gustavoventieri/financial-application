package com.financial.api.framework.shared.hash;

import com.financial.api.auth.application.port.in.password.ComparePasswordUseCase;
import org.springframework.stereotype.Component;

@Component
public class PasswordComparable implements ComparePasswordUseCase {
    @Override
    public boolean execute(String password, String hashPassword) {
        return false;
    }
}
