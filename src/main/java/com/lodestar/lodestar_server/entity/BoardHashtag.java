package com.lodestar.lodestar_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "board_hashtag")
public class BoardHashtag {

    @EmbeddedId
    private BoardHashtagId boardHashtagId;

    @MapsId("boardId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;


}
