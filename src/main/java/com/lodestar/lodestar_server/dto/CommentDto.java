package com.lodestar.lodestar_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "댓글 응답 값들")
public class CommentDto {
    @Schema(description = "댓글 인덱스",example = "1")
    private Long commentId;
    @Schema(description = "댓글 작성자 인덱스", example = "1")
    private Long userId;
    @Schema(description = "댓글 작성자 닉네임", example = "happycyc")
    private String username;
    @Schema(description = "댓글 내용", example = "알고리즘 더 하면 좋을 거 같아요.")
    private String commentContent;
    @Schema(description = "댓글 작성일", example = "2023-07-06T07:23:32.16065")
    private LocalDateTime createdAt;
    @Schema(description = "댓글 수정일", example = "2023-07-06T07:23:32.16065")
    private LocalDateTime modifiedAt;
}