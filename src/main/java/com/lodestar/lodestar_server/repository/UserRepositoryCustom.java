package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findUserWithCareersById(Long userId);
}
