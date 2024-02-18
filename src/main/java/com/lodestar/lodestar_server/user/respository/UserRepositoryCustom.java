package com.lodestar.lodestar_server.user.respository;

import com.lodestar.lodestar_server.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findUserWithCareersById(Long userId);
}
