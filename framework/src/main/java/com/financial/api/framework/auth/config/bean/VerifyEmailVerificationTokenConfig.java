package com.financial.api.framework.auth.config.bean;

import com.financial.api.auth.application.port.in.accessToken.GenerateAccessTokenUseCase;
import com.financial.api.auth.application.port.in.emailVerification.HashEmailVerificationTokenUseCase;
import com.financial.api.auth.application.port.in.refresh.GenerateRefreshTokenUseCase;
import com.financial.api.auth.application.port.in.refresh.HashRefreshTokenUseCase;
import com.financial.api.auth.application.port.in.sign.VerifyEmailVerificationTokenUseCase;

import com.financial.api.auth.application.port.out.EmailVerificationTokenPort;
import com.financial.api.auth.application.port.out.RefreshTokenRepositoryPort;
import com.financial.api.auth.application.service.VerifyEmailVerificationTokenService;
import com.financial.api.shared.email.EmailPort;
import com.financial.api.shared.email.EmailTemplateUseCase;
import com.financial.api.shared.transaction.TransactionManager;
import com.financial.api.user.application.port.out.UserAuthenticationPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VerifyEmailVerificationTokenConfig {

    @Bean
    public VerifyEmailVerificationTokenUseCase verifyEmailVerificationTokenUseCase(
            UserAuthenticationPort userAuthenticationPort,
            EmailVerificationTokenPort emailVerificationTokenPort,
            GenerateAccessTokenUseCase generateAccessTokenUseCase,
            GenerateRefreshTokenUseCase generateRefreshTokenUseCase,
            HashRefreshTokenUseCase hashRefreshTokenUseCase,
            RefreshTokenRepositoryPort refreshTokenRepositoryPort,
            HashEmailVerificationTokenUseCase hashEmailVerificationTokenUseCase,
            TransactionManager transactionManager,
            EmailPort emailPort,
            EmailTemplateUseCase emailTemplateUseCase
    ){
        return new VerifyEmailVerificationTokenService(
            userAuthenticationPort,
            emailVerificationTokenPort,
            generateAccessTokenUseCase,
            generateRefreshTokenUseCase,
            hashRefreshTokenUseCase,
            refreshTokenRepositoryPort,
            hashEmailVerificationTokenUseCase,
            transactionManager,
            emailPort,
            emailTemplateUseCase
        );
    }
}
