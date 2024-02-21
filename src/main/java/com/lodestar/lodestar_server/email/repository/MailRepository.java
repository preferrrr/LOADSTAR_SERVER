package com.lodestar.lodestar_server.email.repository;

import com.lodestar.lodestar_server.email.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailRepository extends JpaRepository<Mail, Long> {

    List<Mail> findByEmail(String email);

    boolean existsByEmail(String email);

    Mail findFirstByEmailOrderByCreatedAtDesc(String email);

}
