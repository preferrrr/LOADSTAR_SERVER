package com.lodestar.lodestar_server.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(name = "수정할 내용")
public class ModifyCommentDto {

    @NotBlank(message = "댓글 내용은 null 또는 공백일 수 없습니다.")
    private String content;


}
