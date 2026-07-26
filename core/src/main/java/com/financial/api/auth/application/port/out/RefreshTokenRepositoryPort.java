package com.financial.api.auth.application.port.out;

import com.financial.api.auth.domain.RefreshToken;

public interface RefreshTokenRepositoryPort {

    void save(RefreshToken refreshToken);
}
