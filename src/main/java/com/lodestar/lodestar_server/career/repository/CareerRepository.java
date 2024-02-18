package com.lodestar.lodestar_server.career.repository;

import com.lodestar.lodestar_server.career.entity.Career;
import com.lodestar.lodestar_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerRepository extends JpaRepository<Career, Long> {

    List<Career> findCareersByUser(User user);

    boolean existsByUser(User user);

}
