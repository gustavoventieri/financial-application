package com.financial.api.user.application.port.out;

import com.financial.api.user.domain.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    User save(User user);
    Optional<User> findById(String id);
}
