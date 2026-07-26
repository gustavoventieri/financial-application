package com.financial.api.auth.application.port.in.accessToken;

import com.financial.api.shared.enumerated.Roles;

public interface GenerateAccessTokenUseCase {
    String execute(String userId, Roles role);
}
