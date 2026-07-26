package com.financial.api.auth.application.port.in.password;

public interface ComparePasswordUseCase {
    boolean execute(String password, String hashPassword);
}
