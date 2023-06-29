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
public class SaveBookmarkDto {
    private Long userId;
    private Long boardId;

    public void validateFieldsNotNull() {
        if(userId == null)
            throw new InvalidRequestParameterException("Invalid userId");
        if(boardId == null)
            throw new InvalidRequestParameterException("Invalid boardId");
    }

}
