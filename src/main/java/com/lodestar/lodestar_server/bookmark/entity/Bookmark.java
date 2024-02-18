package com.lodestar.lodestar_server.bookmark.entity;

import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
