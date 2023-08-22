package com.lodestar.lodestar_server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyCommentDto {

    @Schema(description = "댓글 내용")
    private String commentContent;
    @Schema(description = "댓글 작성 시간")
    private LocalDateTime CommentCreatedAt;
    @Schema(description = "댓글 수정 시간")
    private LocalDateTime CommentModifiedAt;
    @Schema(description = "게시글 id")
    private Long boardId;
    @Schema(description = "게시글 제목")
    private String boardTitle;
    @Schema(description = "조회수")
    private Integer view;
    @Schema(description = "북마크 수")
    private Integer bookmarkCount;

}
