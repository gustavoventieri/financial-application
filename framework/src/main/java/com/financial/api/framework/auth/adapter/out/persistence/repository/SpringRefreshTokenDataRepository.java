package com.financial.api.framework.auth.adapter.out.persistence.repository;

import com.financial.api.framework.auth.adapter.out.persistence.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringRefreshTokenDataRepository extends JpaRepository<RefreshTokenEntity, Long> {
}
