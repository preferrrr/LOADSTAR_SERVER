package com.lodestar.lodestar_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long commentId;
    private Long userId;
    private String username;
    private String commentContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
