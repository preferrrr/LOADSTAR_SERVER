package com.lodestar.lodestar_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetBoardResponseDto {
    private Long boardId;
    private Long userId;
    private String username;
    private String title;
    private String content;
    private String careerImage;
    private boolean bookmark;

    private List<CareerDto> arr;

    private List<String> hashtags;
    private List<GetCommentResponseDto> comments;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
