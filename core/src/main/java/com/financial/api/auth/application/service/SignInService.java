package com.financial.api.auth.application.service;


import com.financial.api.auth.application.dto.response.AuthResponse;
import com.financial.api.auth.application.port.in.accessToken.GenerateAccessTokenUseCase;
import com.financial.api.auth.application.port.in.password.ComparePasswordUseCase;
import com.financial.api.auth.application.port.in.refresh.GenerateRefreshTokenUseCase;
import com.financial.api.auth.application.port.in.refresh.HashRefreshTokenUseCase;
import com.financial.api.auth.application.port.in.sign.SignInUseCase;
import com.financial.api.auth.application.port.out.RefreshTokenRepositoryPort;
import com.financial.api.auth.domain.RefreshToken;
import com.financial.api.shared.exception.BusinessException;
import com.financial.api.shared.exception.NotFoundException;
import com.financial.api.user.application.port.out.UserAuthenticationPort;
import com.financial.api.user.domain.User;


public class SignInService implements SignInUseCase {

    private final UserAuthenticationPort userAuthenticationPort;
    private final ComparePasswordUseCase comparePasswordUseCase;
    private final GenerateAccessTokenUseCase generateAccessTokenUseCase;
    private final GenerateRefreshTokenUseCase generateRefreshTokenUseCase;
    private final HashRefreshTokenUseCase hashRefreshTokenUseCase;
    private final RefreshTokenRepositoryPort refreshTokenRepositoryPort;


    public SignInService(
            UserAuthenticationPort userAuthenticationPort,
            ComparePasswordUseCase comparePasswordUseCase,
            HashRefreshTokenUseCase hashRefreshTokenUseCase,
            GenerateRefreshTokenUseCase generateRefreshTokenUseCase,
            GenerateAccessTokenUseCase generateAccessTokenUseCase,
            RefreshTokenRepositoryPort refreshTokenRepositoryPort){
        this.userAuthenticationPort = userAuthenticationPort;
        this.comparePasswordUseCase = comparePasswordUseCase;
        this.hashRefreshTokenUseCase = hashRefreshTokenUseCase;
        this.generateRefreshTokenUseCase = generateRefreshTokenUseCase;
        this.generateAccessTokenUseCase =  generateAccessTokenUseCase;
        this.refreshTokenRepositoryPort = refreshTokenRepositoryPort;
    }

    @Override
    public AuthResponse execute(String email, String password, String ip, String device) {

        User user = userAuthenticationPort.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email or Password Invalid"));


        if (!user.isVerified()) throw new BusinessException("Email not verified yet");

        boolean passwordMatches = comparePasswordUseCase.execute(
                password,
                user.password()
        );

        if (!passwordMatches) throw new NotFoundException("Email or Password Invalid");

        String accessToken = generateAccessTokenUseCase.execute(
                user.id(),
                user.role()
        );

        String refreshToken = generateRefreshTokenUseCase.execute();

        String refreshTokenHash = hashRefreshTokenUseCase.execute(refreshToken);

        RefreshToken entity = RefreshToken.create(
                user.id(),
                refreshTokenHash,
                ip,
                device
        );

        refreshTokenRepositoryPort.save(entity);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
