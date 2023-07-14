package com.lodestar.lodestar_server.dto;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "수정할 내용")
public class ModifyBoardDto {

    @Schema(description = "수정할 제목", example = "수정할 제목입니다.")
    private String title;
    @Schema(description = "수정할 내용", example = "수정할 내용입니다.")
    private String content;
    @Schema(description = "수정 후 해시태그들", example = "비현직자,비전공자,react")
    private List<String> hashtags;

    public void validateFieldsNotNull() {
        if(title == null || title.isEmpty() || title.isBlank())
            throw new InvalidRequestParameterException("Invalid title");
        if(content == null || content.isEmpty() || content.isBlank())
            throw new InvalidRequestParameterException("Invalid content");
    }
}
