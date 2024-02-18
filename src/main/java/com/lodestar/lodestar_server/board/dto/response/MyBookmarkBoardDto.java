package com.lodestar.lodestar_server.board.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyBookmarkBoardDto {

    @Schema(description = "게시글 id")
    private Long boardId;
    @Schema(description = "게시글 제목")
    private String title;
    @Schema(description = "유저 아이디")
    private String username;
    @Schema(description = "게시글 작성 시간")
    private LocalDateTime createdAt;
    @Schema(description = "게시글 수정 시간")
    private LocalDateTime modifiedAt;
    @Schema(description = "북마크 수")
    private Integer bookmarkCount;
    @Schema(description = "조회수")
    private Integer view;

}
