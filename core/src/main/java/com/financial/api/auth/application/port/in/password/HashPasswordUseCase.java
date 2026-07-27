package com.financial.api.auth.application.port.in.password;

public interface HashPasswordUseCase {
    String execute(String password);
}
