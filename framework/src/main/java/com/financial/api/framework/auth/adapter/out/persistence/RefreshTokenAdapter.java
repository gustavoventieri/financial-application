package com.financial.api.framework.auth.adapter.out.persistence;

import com.financial.api.auth.application.port.out.RefreshTokenRepositoryPort;
import com.financial.api.auth.domain.RefreshToken;
import com.financial.api.framework.auth.adapter.cast.RefreshTokenCast;
import com.financial.api.framework.auth.adapter.out.persistence.repository.SpringRefreshTokenDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenAdapter implements RefreshTokenRepositoryPort {

    private final SpringRefreshTokenDataRepository springRefreshTokenDataRepository;

    public RefreshTokenAdapter(SpringRefreshTokenDataRepository springRefreshTokenDataRepository){
        this.springRefreshTokenDataRepository = springRefreshTokenDataRepository;
    }

    @Override
    public void save(RefreshToken refreshToken) {
        springRefreshTokenDataRepository.save(RefreshTokenCast.toEntity(refreshToken));
    }
}
