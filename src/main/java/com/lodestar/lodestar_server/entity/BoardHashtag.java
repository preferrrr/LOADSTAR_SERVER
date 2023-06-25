package com.lodestar.lodestar_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"hashtag_name","board_id"})})
public class BoardHashtag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_hashtag_id")
    Long id;

    @Column(name = "hashtag_name", nullable = false)
    String hashtagName;

    @JoinColumn(name = "board_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    Board board;


}
