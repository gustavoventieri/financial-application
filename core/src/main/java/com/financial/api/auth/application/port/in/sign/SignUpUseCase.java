package com.financial.api.auth.application.port.in.sign;

public interface SignUpUseCase {
    String execute(String name, String email, String password);
}
