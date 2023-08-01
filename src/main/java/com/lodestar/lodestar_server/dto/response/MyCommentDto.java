package com.lodestar.lodestar_server.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyCommentDto {
    private Long commentId;
    private Long boardId;
    private String content;
}
