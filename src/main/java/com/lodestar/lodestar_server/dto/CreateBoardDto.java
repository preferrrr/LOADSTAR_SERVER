package com.lodestar.lodestar_server.dto;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardDto {
    private Long userId;
    private String title;
    private String content;
    private String showGraph;
    private List<String> hashtags = new ArrayList<>();

    public void validateFieldsNotNull() {
        if(userId == null)
            throw new InvalidRequestParameterException("Invalid userId");
        if(title == null || title.isEmpty() || title.isBlank())
            throw new InvalidRequestParameterException("Invalid title");
        if(content == null || content.isEmpty() || content.isBlank())
            throw new InvalidRequestParameterException("Invalid content");
        if(showGraph == null || showGraph.isEmpty() || showGraph.isBlank())
            throw new InvalidRequestParameterException("Invalid showGraph");
    }
}
