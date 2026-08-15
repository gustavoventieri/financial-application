package com.financial.api.framework.auth.config.bean;

import com.financial.api.auth.application.port.in.refresh.CompareRefreshTokenUseCase;
import com.financial.api.auth.application.port.in.sign.RevokeRefreshTokenUseCase;
import com.financial.api.auth.application.port.out.RefreshTokenPersistencePort;
import com.financial.api.auth.application.service.RevokeRefreshTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RevokeRefreshTokenConfigBean {
    @Bean
    public RevokeRefreshTokenUseCase revokeRefreshTokenUseCaseConfigBean(
            RefreshTokenPersistencePort refreshTokenRepositoryPort
    ){
        return new RevokeRefreshTokenService(
                refreshTokenRepositoryPort
        );
    }

}
