package com.lodestar.lodestar_server.hashtag.entity;

import com.lodestar.lodestar_server.board.entity.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board_hashtag")
public class BoardHashtag {

    @EmbeddedId
    private BoardHashtagId id;

    @MapsId("boardId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    private BoardHashtag(Board board, String hashtagName) {
        BoardHashtagId id = BoardHashtagId.builder()
                .boardId(board.getId())
                .hashtagName(hashtagName)
                .build();

        this.id = id;
        this.board = board;
    }

    public static BoardHashtag create(Board board, String hashtagName) {
        return BoardHashtag.builder()
                .board(board)
                .hashtagName(hashtagName)
                .build();
    }
}
