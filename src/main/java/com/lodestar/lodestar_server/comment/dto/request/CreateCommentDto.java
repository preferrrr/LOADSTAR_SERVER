package com.lodestar.lodestar_server.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(name = "작성 내용")
public class CreateCommentDto {

    @Schema(name = "게시글 id", description = "댓글 남길 게시글")
    @NotBlank(message = "게시글 인덱스는 null 또는 공백일 수 없습니다.")
    private Long boardId;

    @Schema(name = "댓글 내용")
    @NotBlank(message = "댓글 내용은 null 또는 공백일 수 없습니다.")
    private String content;

}
