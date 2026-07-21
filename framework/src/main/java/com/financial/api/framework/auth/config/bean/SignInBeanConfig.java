package com.financial.api.framework.auth.config.bean;

import com.financial.api.auth.application.port.in.sign.SignInUseCase;
import com.financial.api.auth.application.service.SignInService;
import com.financial.api.user.application.port.out.UserAuthenticationPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignInBeanConfig {

    @Bean
    public SignInUseCase signInUseCase(
            UserAuthenticationPort userAuthenticationPort
    ) {
        return new SignInService(userAuthenticationPort);
    }
}