package com.financial.api.auth.application.service;

import com.financial.api.auth.application.dto.response.AuthResponse;
import com.financial.api.auth.application.port.in.accessToken.GenerateAccessTokenUseCase;
import com.financial.api.auth.application.port.in.refresh.CompareRefreshTokenUseCase;
import com.financial.api.auth.application.port.in.sign.RefreshTokenUseCase;
import com.financial.api.auth.application.port.out.RefreshTokenPersistencePort;
import com.financial.api.auth.domain.RefreshToken;
import com.financial.api.shared.exception.BusinessException;
import com.financial.api.shared.exception.NotFoundException;
import com.financial.api.user.application.port.out.UserAuthenticationPersistencePort;
import com.financial.api.user.domain.User;



public class RefreshTokenService implements RefreshTokenUseCase {

    private final RefreshTokenPersistencePort refreshTokenRepositoryPort;
    private final UserAuthenticationPersistencePort userAuthenticationPersistencePort;
    private final CompareRefreshTokenUseCase compareRefreshTokenUseCase;
    private final GenerateAccessTokenUseCase generateAccessTokenUseCase;

    public RefreshTokenService(
            RefreshTokenPersistencePort refreshTokenRepositoryPort,
            UserAuthenticationPersistencePort userAuthenticationPersistencePort,
            CompareRefreshTokenUseCase compareRefreshTokenUseCase,
            GenerateAccessTokenUseCase generateAccessTokenUseCase
    ){
        this.refreshTokenRepositoryPort = refreshTokenRepositoryPort;
        this.compareRefreshTokenUseCase = compareRefreshTokenUseCase;
        this.generateAccessTokenUseCase = generateAccessTokenUseCase;
        this.userAuthenticationPersistencePort = userAuthenticationPersistencePort;
    }

    @Override
    public AuthResponse execute(String refreshToken) {

        RefreshToken storedToken = refreshTokenRepositoryPort
                .findRefreshTokenByHashRefreshToken(refreshToken)
                .orElseThrow(() ->
                        new NotFoundException("Invalid refresh token")
                );

        if (storedToken.isExpired()) {

            refreshTokenRepositoryPort.save(storedToken.revoke());

            throw new BusinessException("Refresh token expired");
        }

        if (storedToken.isRevoked()) {
            throw new BusinessException("Refresh token revoked");
        }

        if (!compareRefreshTokenUseCase.execute(
                refreshToken,
                storedToken.tokenHash()
        )) {
            throw new BusinessException("Invalid refresh token");
        }

        User user = userAuthenticationPersistencePort.findById(storedToken.userId())
                .orElseThrow(() ->
                        new NotFoundException("User Not Found")
                );;

        String accessToken = generateAccessTokenUseCase.execute(
                storedToken.userId(),
                user.role()
        );

        return new AuthResponse(
                accessToken,
                refreshToken
        );
    }

    @Override
    public void revokeToken(String refreshToken) {

    }
}
