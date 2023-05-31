package com.lodestar.lodestar_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Explain{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "explain_id")
    Long id;

    @OneToOne
    @JoinColumn(name = "career_id")
    Career career;

    @Column(nullable = false)
    String explain;
}
