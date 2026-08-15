package com.financial.api.auth.application.service;

import com.financial.api.auth.application.port.in.refresh.CompareRefreshTokenUseCase;

import com.financial.api.auth.application.port.in.sign.RevokeRefreshTokenUseCase;
import com.financial.api.auth.application.port.out.RefreshTokenPersistencePort;
import com.financial.api.auth.domain.RefreshToken;
import com.financial.api.shared.exception.BusinessException;

public class RevokeRefreshTokenService implements RevokeRefreshTokenUseCase {

    private final RefreshTokenPersistencePort refreshTokenRepositoryPort;

    public RevokeRefreshTokenService(
            RefreshTokenPersistencePort refreshTokenRepositoryPort
    ) {
        this.refreshTokenRepositoryPort = refreshTokenRepositoryPort;
    }

    @Override
    public void execute(String sessionId, String userId) {

        RefreshToken refreshToken =
                refreshTokenRepositoryPort
                        .findByPublicIdAndUserId(sessionId, userId)
                        .orElseThrow(() ->
                                new BusinessException("Session not found")
                        );

        if (refreshToken.isRevoked()) {
            throw new BusinessException("Session already revoked");
        }

        refreshTokenRepositoryPort.save(
                refreshToken.revoke()
        );
    }
}