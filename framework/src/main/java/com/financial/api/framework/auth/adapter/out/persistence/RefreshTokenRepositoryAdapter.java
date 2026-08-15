package com.financial.api.framework.auth.adapter.out.persistence;

import com.financial.api.auth.application.port.out.RefreshTokenPersistencePort;
import com.financial.api.auth.domain.RefreshToken;
import com.financial.api.framework.auth.adapter.cast.RefreshTokenCast;
import com.financial.api.framework.auth.adapter.out.persistence.repository.SpringRefreshTokenDataRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RefreshTokenRepositoryAdapter implements RefreshTokenPersistencePort {

    private final SpringRefreshTokenDataRepository springRefreshTokenDataRepository;

    public RefreshTokenRepositoryAdapter(SpringRefreshTokenDataRepository springRefreshTokenDataRepository){
        this.springRefreshTokenDataRepository = springRefreshTokenDataRepository;
    }

    @Override
    public void save(RefreshToken refreshToken) {
        springRefreshTokenDataRepository.save(RefreshTokenCast.toEntity(refreshToken));
    }

    @Override
    public Optional<RefreshToken> findRefreshTokenByHashRefreshToken(String refreshToken) {
        return springRefreshTokenDataRepository.findByTokenHash(refreshToken).map(RefreshTokenCast::toDomain);
    }
}
