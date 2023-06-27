package com.lodestar.lodestar_server.dto;

import com.lodestar.lodestar_server.entity.BoardHashtag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardPagingDto {
    private Long boardId;
    private String title;
    private List<String> hashtags;
    private String careerImage;
}
