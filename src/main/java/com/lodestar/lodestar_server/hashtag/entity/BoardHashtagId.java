package com.lodestar.lodestar_server.hashtag.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BoardHashtagId implements Serializable {

    private Long boardId;

    @Column(name = "hashtag_name")
    private String hashtagName;

    @Builder
    public BoardHashtagId (Long boardId, String hashtagName) {
        this.boardId = boardId;
        this.hashtagName = hashtagName;
    }
}
