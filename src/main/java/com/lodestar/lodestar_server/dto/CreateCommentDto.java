package com.lodestar.lodestar_server.dto;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateCommentDto {

    private Long boardId;
    private String content;

    public void validateFieldsNotNull() {
        if(content == null || content.isEmpty() || content.isBlank())
            throw new InvalidRequestParameterException("Invalid content");
        if(boardId == null)
            throw new InvalidRequestParameterException("Invalid boardId");
    }
}
