package com.lodestar.lodestar_server.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyBoardDto {
    private Long boardId;
    private String title;
    private Integer bookmarkCount;
    private Integer view;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
