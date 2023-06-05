package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRepository extends JpaRepository<Mail, Long> {

    List<Mail> findByEmail(String email);

}
