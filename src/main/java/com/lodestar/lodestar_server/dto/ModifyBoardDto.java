package com.lodestar.lodestar_server.dto;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyBoardDto {

    private String title;
    private String content;

    public void validateFieldsNotNull() {
        if(title == null || title.isEmpty() || title.isBlank())
            throw new InvalidRequestParameterException("Invalid title");
        if(content == null || content.isEmpty() || content.isBlank())
            throw new InvalidRequestParameterException("Invalid content");
    }
}
