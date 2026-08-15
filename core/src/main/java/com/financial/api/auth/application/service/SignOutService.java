package com.financial.api.auth.application.service;

import com.financial.api.auth.application.port.in.refresh.CompareRefreshTokenUseCase;
import com.financial.api.auth.application.port.in.refresh.HashRefreshTokenUseCase;
import com.financial.api.auth.application.port.in.sign.SignOutUseCase;
import com.financial.api.auth.application.port.out.RefreshTokenPersistencePort;
import com.financial.api.auth.domain.RefreshToken;
import com.financial.api.shared.exception.BusinessException;

public class SignOutService implements SignOutUseCase {

    private final RefreshTokenPersistencePort refreshTokenRepositoryPort;
    private final CompareRefreshTokenUseCase compareRefreshTokenUseCase;
    private final HashRefreshTokenUseCase hashRefreshTokenUseCase;

    public SignOutService(
            RefreshTokenPersistencePort refreshTokenRepositoryPort,
            CompareRefreshTokenUseCase compareRefreshTokenUseCase,
            HashRefreshTokenUseCase hashRefreshTokenUseCase
    ) {
        this.refreshTokenRepositoryPort = refreshTokenRepositoryPort;
        this.compareRefreshTokenUseCase = compareRefreshTokenUseCase;
        this.hashRefreshTokenUseCase = hashRefreshTokenUseCase;
    }

    @Override
    public void execute(String refreshToken) {

        RefreshToken storedToken =
                refreshTokenRepositoryPort
                        .findRefreshTokenByHashRefreshToken(hashRefreshTokenUseCase.execute(refreshToken))
                        .orElseThrow(() ->
                                new BusinessException("Invalid refresh token")
                        );

        if (storedToken.isRevoked()) {
            return;
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