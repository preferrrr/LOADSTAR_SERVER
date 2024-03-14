package com.lodestar.lodestar_server.bookmark.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "북마크 등록할 게시글 id")
public class SaveBookmarkDto {

    @Schema(description = "북마크 등록할 게시글 id")
    @NotBlank(message = "게시글 인덱스는 null 또는 공백일 수 없습니다.")
    private Long boardId;


}
