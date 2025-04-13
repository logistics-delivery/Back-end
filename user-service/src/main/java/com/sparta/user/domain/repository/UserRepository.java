package com.sparta.user.domain.repository;

import com.sparta.user.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
