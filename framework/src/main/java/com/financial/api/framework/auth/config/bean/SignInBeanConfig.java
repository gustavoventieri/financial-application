package com.financial.api.framework.auth.config.bean;

import com.financial.api.auth.application.port.in.accessToken.GenerateAccessTokenUseCase;
import com.financial.api.auth.application.port.in.password.ComparePasswordUseCase;
import com.financial.api.auth.application.port.in.refresh.GenerateRefreshTokenUseCase;
import com.financial.api.auth.application.port.in.refresh.HashRefreshTokenUseCase;
import com.financial.api.auth.application.port.in.sign.SignInUseCase;
import com.financial.api.auth.application.port.out.RefreshTokenPersistencePort;
import com.financial.api.auth.application.service.SignInService;
import com.financial.api.shared.email.EmailPort;
import com.financial.api.shared.email.EmailTemplateUseCase;
import com.financial.api.user.application.port.out.UserAuthenticationPersistencePort;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignInBeanConfig {

    @Bean
    public SignInUseCase signInUseCaseBean(
            UserAuthenticationPersistencePort userAuthenticationPort,
            ComparePasswordUseCase comparePasswordUseCase,
            HashRefreshTokenUseCase hashRefreshTokenUseCase,
            GenerateRefreshTokenUseCase generateRefreshTokenUseCase,
            GenerateAccessTokenUseCase generateAccessTokenUseCase,
            RefreshTokenPersistencePort refreshTokenRepositoryPort,
            EmailPort emailPort,
            EmailTemplateUseCase emailTemplateUseCase
    ) {
        return new SignInService(
                userAuthenticationPort,
                comparePasswordUseCase,
                hashRefreshTokenUseCase,
                generateRefreshTokenUseCase,
                generateAccessTokenUseCase,
                refreshTokenRepositoryPort,
                emailPort,
                emailTemplateUseCase
        );
    }
}