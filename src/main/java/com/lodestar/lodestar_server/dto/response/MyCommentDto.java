package com.lodestar.lodestar_server.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyCommentDto {

    private String commentContent;
    private LocalDateTime CommentCreatedAt;
    private LocalDateTime CommentModifiedAt;
    private Long boardId;
    private String boardTitle;
    private Integer view;
    private Integer bookmarkCount;

}
