package com.lodestar.lodestar_server.comment.dto.response;

import com.lodestar.lodestar_server.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Schema(description = "댓글 응답 값들")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentListDto {

    private List<CommentDto> commentDtoList;

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    static class CommentDto {
        @Schema(description = "댓글 인덱스", example = "1")
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

        @Builder
        private CommentDto(Long commentId, Long userId, String username, String commentContent, LocalDateTime createdAt, LocalDateTime modifiedAt) {
            this.commentId = commentId;
            this.userId = userId;
            this.username = username;
            this.commentContent = commentContent;
            this.createdAt = createdAt;
            this.modifiedAt = modifiedAt;
        }

        public static CommentDto of(Comment comment) {
            return CommentDto.builder()
                    .commentId(comment.getId())
                    .userId(comment.getUser().getId())
                    .username(comment.getUser().getUsername())
                    .commentContent(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .build();
        }

    }


    @Builder
    private CommentListDto(List<CommentDto> commentDtoList) {
        this.commentDtoList = commentDtoList;
    }

    public static CommentListDto of(List<Comment> comments) {
        return CommentListDto.builder()
                .commentDtoList(comments.stream()
                        .map(comment -> CommentDto.of(comment))
                        .collect(Collectors.toList()))
                .build();
    }


}
