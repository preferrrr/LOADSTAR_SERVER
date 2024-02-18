package com.lodestar.lodestar_server.email.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "mail")
@EntityListeners(value = {AuditingEntityListener.class})
public class Mail implements Persistable<Long> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mail_id")
    Long id;

    @Column(name = "email",nullable = false)
    String email;

    @Column(name = "auth_key", nullable = false)
    String authKey;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }

    @Builder
    public Mail(String email, String authKey) {
        this.email = email;
        this.authKey = authKey;
    }
}
