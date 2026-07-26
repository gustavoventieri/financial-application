package com.financial.api.framework.auth.adapter.out.persistence;

import com.financial.api.auth.application.port.out.EmailVerificationTokenPort;
import com.financial.api.framework.auth.adapter.out.persistence.repository.SpringEmailVerificationTokenDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EmailVerificationTokenAdapter implements EmailVerificationTokenPort {

    private final SpringEmailVerificationTokenDataRepository springEmailVerificationTokenDataRepository;

    public EmailVerificationTokenAdapter(SpringEmailVerificationTokenDataRepository springEmailVerificationTokenDataRepository){
        this.springEmailVerificationTokenDataRepository = springEmailVerificationTokenDataRepository;
    }


}
