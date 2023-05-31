package com.lodestar.lodestar_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "select")
public class Select {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "select_id")
    Long id;

    @OneToOne
    @JoinColumn(name = "career_id")
    Career career;

    @Column(nullable = false)
    String selected;

}
