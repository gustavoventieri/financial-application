package com.financial.api.framework.auth.config.bean;


import com.financial.api.auth.application.port.in.emailVerification.GenerateEmailVerificationTokenUseCase;
import com.financial.api.auth.application.port.in.emailVerification.HashEmailVerificationTokenUseCase;
import com.financial.api.auth.application.port.in.password.HashPasswordUseCase;
import com.financial.api.auth.application.port.in.sign.SignUpUseCase;
import com.financial.api.auth.application.port.out.EmailVerificationTokenPort;
import com.financial.api.auth.application.service.SignUpService;
import com.financial.api.shared.email.EmailPort;
import com.financial.api.shared.email.EmailTemplateUseCase;
import com.financial.api.shared.transaction.TransactionManager;
import com.financial.api.user.application.port.out.UserAuthenticationPersistencePort;
import com.financial.api.user.application.port.out.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignUpBeanConfig {

    @Bean
    public SignUpUseCase signUpUseCaseBean(
            UserAuthenticationPersistencePort userAuthenticationPort,
            UserRepositoryPort userRepositoryPort,
            EmailVerificationTokenPort emailVerificationTokenRepositoryPort,
            HashPasswordUseCase hashPasswordUseCase,
            GenerateEmailVerificationTokenUseCase generateEmailVerificationTokenUseCase,
            HashEmailVerificationTokenUseCase hashEmailVerificationTokenUseCase,
            EmailPort emailPort,
            EmailTemplateUseCase emailTemplateUseCase,
            TransactionManager transactionManager
    ){
        return new SignUpService(
                 userAuthenticationPort,
                 userRepositoryPort,
                 emailVerificationTokenRepositoryPort,
                 hashPasswordUseCase,
                 generateEmailVerificationTokenUseCase,
                 hashEmailVerificationTokenUseCase,
                 emailPort,
                 emailTemplateUseCase,
                 transactionManager
        );
    }
}
