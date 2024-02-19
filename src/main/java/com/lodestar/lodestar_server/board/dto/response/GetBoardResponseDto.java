package com.lodestar.lodestar_server.board.dto.response;

import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.career.dto.response.CareerListDto;
import com.lodestar.lodestar_server.comment.dto.response.CommentListDto;
import com.lodestar.lodestar_server.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Schema(description = "게시글 조회 응답")
public class GetBoardResponseDto {

    @Schema(description = "게시글 인덱스", example = "1")
    private Long boardId;
    @Schema(description = "게시글 작성자 인덱스", example = "1")
    private Long userId;
    @Schema(description = "게시글 작성자 닉네임", example = "happycyc")
    private String username;
    @Schema(description = "게시글 제목", example = "안녕하세요 !")
    private String title;
    @Schema(description = "게시글 내용", example = "이 다음으로 어떤 공부를 하면 좋을까요 ?")
    private String content;
    @Schema(description = "게시글 조회한 유저가 이 글을 북마크로 등록했는지 여부", example = "true")
    private Boolean isBookmarked;

    @Schema(description = "북마크 갯수")
    private Integer bookmarkCount;

    @Schema(description = "조회수")
    private Integer view;

    @Schema(description = "게시글 작성자의 경력들")
    private CareerListDto careers;

    @Schema(description = "게시글에 태그된 해시태그들", example = "전공자,비현직자,java,알고리즘,질문글")
    private List<String> hashtags;

    @Schema(description = "게시글에 달린 댓글들")
    private CommentListDto comments;

    @Schema(description = "작성일", example = "2023-07-06T07:23:32.16065")
    private LocalDateTime createdAt;
    @Schema(description = "수정일", example = "2023-07-06T07:23:32.16065")
    private LocalDateTime modifiedAt;

    @Builder
    private GetBoardResponseDto(Long boardId, Long userId, String username, String title, String content, boolean isBookmarked, Integer bookmarkCount, Integer view, CareerListDto careers, List<String> hashtags, CommentListDto comments, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.boardId = boardId;
        this.userId = userId;
        this.username = username;
        this.title = title;
        this.content = content;
        this.isBookmarked = isBookmarked;
        this.bookmarkCount = bookmarkCount;
        this.view = view;
        this.careers = careers;
        this.hashtags = hashtags;
        this.comments = comments;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static GetBoardResponseDto of(Board board, List<Comment> comments, boolean isBookmarked) {
        return GetBoardResponseDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .view(board.getView())
                .bookmarkCount(board.getBookmarkCount())
                .userId(board.getUser().getId())
                .username(board.getUser().getUsername())
                .careers(CareerListDto.of(board.getUser().getCareers()))
                .isBookmarked(isBookmarked)
                .hashtags(board.getHashtags().stream()
                        .map(hashtag -> hashtag.getId().getHashtagName())
                        .collect(Collectors.toList()))
                .comments(CommentListDto.of(comments))
                .build();
    }
}
