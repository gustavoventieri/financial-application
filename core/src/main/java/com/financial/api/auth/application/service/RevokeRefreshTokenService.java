package com.financial.api.auth.application.service;

import com.financial.api.auth.application.port.in.refresh.CompareRefreshTokenUseCase;

import com.financial.api.auth.application.port.in.sign.RevokeRefreshTokenUseCase;
import com.financial.api.auth.application.port.out.RefreshTokenPersistencePort;
import com.financial.api.auth.domain.RefreshToken;
import com.financial.api.shared.exception.BusinessException;

public class RevokeRefreshTokenService implements RevokeRefreshTokenUseCase {

    private final RefreshTokenPersistencePort refreshTokenRepositoryPort;
    private final CompareRefreshTokenUseCase compareRefreshTokenUseCase;

    public RevokeRefreshTokenService(
            RefreshTokenPersistencePort refreshTokenRepositoryPort,
            CompareRefreshTokenUseCase compareRefreshTokenUseCase
    ) {
        this.refreshTokenRepositoryPort = refreshTokenRepositoryPort;
        this.compareRefreshTokenUseCase = compareRefreshTokenUseCase;
    }

    @Override
    public void execute(String refreshToken) {

        RefreshToken storedToken = refreshTokenRepositoryPort
                .findRefreshTokenByHashRefreshToken(refreshToken)
                .orElseThrow(() ->
                        new BusinessException("Invalid refresh token")
                );

        if (storedToken.isRevoked()) {
            throw new BusinessException("Refresh token already revoked");
        }

        if (!compareRefreshTokenUseCase.execute(
                refreshToken,
                storedToken.tokenHash()
        )) {
            throw new BusinessException("Invalid refresh token");
        }

        refreshTokenRepositoryPort.save(
                storedToken.revoke()
        );
    }
}