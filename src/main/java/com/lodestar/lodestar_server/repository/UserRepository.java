package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findById(Long id);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);


}
