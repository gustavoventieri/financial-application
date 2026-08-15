package com.financial.api.framework.auth.config.bean;

import com.financial.api.auth.application.port.in.accessToken.GenerateAccessTokenUseCase;
import com.financial.api.auth.application.port.in.refresh.CompareRefreshTokenUseCase;
import com.financial.api.auth.application.port.in.sign.RefreshTokenUseCase;
import com.financial.api.auth.application.port.out.RefreshTokenPersistencePort;
import com.financial.api.auth.application.service.RefreshTokenService;
import com.financial.api.user.application.port.out.UserAuthenticationPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RefreshTokenBeanConfig {

    @Bean
    public RefreshTokenUseCase refreshTokenUseCaseConfigBean(
            RefreshTokenPersistencePort refreshTokenRepositoryPort,
            UserAuthenticationPersistencePort userAuthenticationPersistencePort,
            CompareRefreshTokenUseCase compareRefreshTokenUseCase,
            GenerateAccessTokenUseCase generateAccessTokenUseCase
    ){
        return new RefreshTokenService(
                refreshTokenRepositoryPort,
                userAuthenticationPersistencePort,
                compareRefreshTokenUseCase,
                generateAccessTokenUseCase
        );
    }
}
