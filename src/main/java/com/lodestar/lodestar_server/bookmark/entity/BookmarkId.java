package com.lodestar.lodestar_server.bookmark.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookmarkId implements Serializable {

    private Long boardId;
    private Long userId;

    @Builder
    public BookmarkId(Long boardId, Long userId) {
        this.boardId = boardId;
        this.userId = userId;
    }

}
