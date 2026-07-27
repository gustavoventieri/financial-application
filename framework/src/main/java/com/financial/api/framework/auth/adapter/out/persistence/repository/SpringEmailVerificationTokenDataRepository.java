package com.financial.api.framework.auth.adapter.out.persistence.repository;


import com.financial.api.framework.auth.adapter.out.persistence.entity.EmailVerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringEmailVerificationTokenDataRepository extends JpaRepository<EmailVerificationTokenEntity, Long> {
    Optional<EmailVerificationTokenEntity> findByUserId(String userId);

    void deleteByUserId(String id);
}
