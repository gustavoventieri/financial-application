package com.financial.api.auth.application.port.in.accessToken;

import com.financial.api.shared.enumerated.Roles;

public interface ExtractRoleFromAccessTokenUseCase {
    Roles execute(String token);
}
