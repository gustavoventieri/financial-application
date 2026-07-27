package com.financial.api.auth.application.service;

import com.financial.api.auth.application.port.in.emailVerification.GenerateEmailVerificationTokenUseCase;
import com.financial.api.auth.application.port.in.password.HashPasswordUseCase;
import com.financial.api.auth.application.port.in.sign.SignUpUseCase;
import com.financial.api.auth.domain.EmailVerificationToken;
import com.financial.api.shared.enumerated.Roles;
import com.financial.api.shared.exception.BusinessException;
import com.financial.api.user.application.port.out.UserAuthenticationPort;
import com.financial.api.user.domain.User;

import java.time.LocalDateTime;


public class SignUpService implements SignUpUseCase {

    private final UserAuthenticationPort userAuthenticationPort;
    private final HashPasswordUseCase hashPasswordUseCase;
    private final GenerateEmailVerificationTokenUseCase generateEmailVerificationTokenUseCase;

    public SignUpService(
            UserAuthenticationPort userAuthenticationPort,
            HashPasswordUseCase hashPasswordUseCase,
            GenerateEmailVerificationTokenUseCase generateEmailVerificationTokenUseCase
    ){
        this.userAuthenticationPort = userAuthenticationPort;
        this.hashPasswordUseCase = hashPasswordUseCase;
        this.generateEmailVerificationTokenUseCase = generateEmailVerificationTokenUseCase;
    }

    @Override
    public String execute(String name, String email, String password) {

        User user = userAuthenticationPort.findByEmail(email).orElse(null);

        if (user != null) {
            if (user.isVerified()) {
                throw new BusinessException("Email already registered");
            }
            handlePendingRegistration(user, name, password);
            return "If the registration can proceed, a verification code has been sent to the provided email.";
        }


        String hashedPassword = hashPasswordUseCase.execute(password);

        User newUser = User.builder()
                .name(name)
                .email(email)
                .password(hashedPassword)
                .role(Roles.USER)
                .isVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        EmailVerificationToken emailVerificationToken = EmailVerificationToken.

        return "";

    }



    private void handlePendingRegistration(
            User user,
            String name,
            String password
    ) {

    }
}
