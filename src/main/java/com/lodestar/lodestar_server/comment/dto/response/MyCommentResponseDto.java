package com.lodestar.lodestar_server.comment.dto.response;

import com.lodestar.lodestar_server.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MyCommentResponseDto {

    private List<MyCommentDto> comments;

    static class MyCommentDto {
        @Schema(description = "댓글 내용")
        private String commentContent;
        @Schema(description = "댓글 작성 시간")
        private LocalDateTime commentCreatedAt;
        @Schema(description = "댓글 수정 시간")
        private LocalDateTime commentModifiedAt;
        @Schema(description = "게시글 id")
        private Long boardId;
        @Schema(description = "게시글 제목")
        private String boardTitle;
        @Schema(description = "조회수")
        private Integer view;
        @Schema(description = "북마크 수")
        private Integer bookmarkCount;

        @Builder
        private MyCommentDto(String commentContent, LocalDateTime commentCreatedAt, LocalDateTime commentModifiedAt, Long boardId, String boardTitle, Integer view, Integer bookmarkCount) {
            this.commentContent = commentContent;
            this.commentCreatedAt = commentCreatedAt;
            this.commentModifiedAt = commentModifiedAt;
            this.boardId = boardId;
            this.boardTitle = boardTitle;
            this.view = view;
            this.bookmarkCount = bookmarkCount;
        }

        public static MyCommentDto of(Comment comment) {
            return MyCommentDto.builder()
                    .commentContent(comment.getContent())
                    .commentCreatedAt(comment.getCreatedAt())
                    .commentModifiedAt(comment.getModifiedAt())
                    .boardId(comment.getBoard().getId())
                    .boardTitle(comment.getBoard().getTitle())
                    .view(comment.getBoard().getView())
                    .bookmarkCount(comment.getBoard().getBookmarkCount())
                    .build();
        }
    }

    @Builder
    private MyCommentResponseDto(List<MyCommentDto> comments) {
        this.comments = comments;
    }

    public static MyCommentResponseDto of(List<Comment> comments) {
        return MyCommentResponseDto.builder()
                .comments(comments.stream()
                        .map(comment -> MyCommentDto.of(comment))
                        .collect(Collectors.toList()))
                .build();
    }
}
