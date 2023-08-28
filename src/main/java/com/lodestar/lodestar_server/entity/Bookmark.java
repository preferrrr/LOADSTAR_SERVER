package com.lodestar.lodestar_server.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bookmark")
public class Bookmark {

    @EmbeddedId
    private BookmarkId id;

    @MapsId("boardId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Bookmark(Board board, User user) {
        BookmarkId id = BookmarkId.builder()
                .boardId(board.getId())
                .userId(user.getId())
                .build();

        this.id = id;
        this.board = board;
        this.user = user;
    }

}
