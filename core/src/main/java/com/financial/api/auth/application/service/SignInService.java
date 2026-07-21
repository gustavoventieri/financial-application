package com.financial.api.auth.application.service;

import com.financial.api.auth.application.dto.request.SignInRequest;
import com.financial.api.auth.application.dto.response.AuthResponse;
import com.financial.api.auth.application.port.in.sign.SignInUseCase;
import com.financial.api.user.application.port.out.UserAuthenticationPort;



public class SignInService implements SignInUseCase {

    private final UserAuthenticationPort userAuthenticationPort;

    public SignInService(UserAuthenticationPort userAuthenticationPort){
        this.userAuthenticationPort = userAuthenticationPort;
    }

    @Override
    public AuthResponse execute(SignInRequest request) {
        return null;
    }
}
