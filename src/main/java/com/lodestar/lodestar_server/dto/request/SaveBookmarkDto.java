package com.lodestar.lodestar_server.dto.request;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "북마크 등록할 게시글 id")
public class SaveBookmarkDto {
    @Schema(description = "북마크 등록할 게시글 id")
    private Long boardId;

    public void validateFieldsNotNull() {
        if(boardId == null)
            throw new InvalidRequestParameterException("Invalid boardId");
    }

}
