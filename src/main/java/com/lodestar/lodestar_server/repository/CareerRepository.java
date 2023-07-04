package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Career;
import com.lodestar.lodestar_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerRepository extends JpaRepository<Career, Long> {

    List<Career> findCareersByUser(User user);

}
