package com.lodestar.lodestar_server.dto.request;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(name = "작성 내용")
public class CreateCommentDto {

    @Schema(name = "게시글 id", description = "댓글 남길 게시글")
    private Long boardId;
    @Schema(name = "댓글 내용")
    private String content;

    public void validateFieldsNotNull() {
        if(content == null || content.isEmpty() || content.isBlank())
            throw new InvalidRequestParameterException("Invalid content");
        if(boardId == null)
            throw new InvalidRequestParameterException("Invalid boardId");
    }
}
