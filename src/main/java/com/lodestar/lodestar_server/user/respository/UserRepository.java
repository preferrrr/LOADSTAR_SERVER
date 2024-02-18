package com.lodestar.lodestar_server.user.respository;

import com.lodestar.lodestar_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    /** querydsl로 대체*/
    @Query("select distinct u from User u " +
            "left join fetch u.careers c " +
            "where u.id = :id")
    Optional<User> findByIdWithCareers(@Param("id") Long id);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    boolean existsByEmailAndUsername(String email, String username);



}
