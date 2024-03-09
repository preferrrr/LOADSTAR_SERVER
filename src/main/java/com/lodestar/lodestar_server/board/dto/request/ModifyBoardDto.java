package com.lodestar.lodestar_server.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "수정할 내용")
public class ModifyBoardDto {

    @Schema(description = "수정할 제목", example = "수정할 제목입니다.")
    @NotBlank(message = "공백 또는 null일 수 없습니다.")
    private String title;

    @Schema(description = "수정할 내용", example = "수정할 내용입니다.")
    @NotBlank(message = "공백 또는 null일 수 없습니다.")
    private String content;

    @Schema(description = "수정 후 해시태그들", example = "비현직자,비전공자,react")
    private List<String> hashtags;

}
