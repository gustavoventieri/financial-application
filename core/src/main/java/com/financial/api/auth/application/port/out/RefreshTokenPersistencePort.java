package com.financial.api.auth.application.port.out;

import com.financial.api.auth.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenPersistencePort {

    void save(RefreshToken refreshToken);
    Optional<RefreshToken> findRefreshTokenByHashRefreshToken(String refreshToken);
    Optional<RefreshToken> findByPublicIdAndUserId(
            String publicId,
            String userId
    );
}
