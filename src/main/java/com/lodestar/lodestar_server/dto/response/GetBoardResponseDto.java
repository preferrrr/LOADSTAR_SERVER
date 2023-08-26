package com.lodestar.lodestar_server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
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
    private boolean bookmark;

    @Schema(description = "북마크 갯수")
    private Integer bookmarkCount;

    @Schema(description = "조회수")
    private Integer view;

    @Schema(description = "게시글 작성자의 경력들")
    private List<CareerDto> arr;

    @Schema(description = "게시글에 태그된 해시태그들", example = "전공자,비현직자,java,알고리즘,질문글")
    private List<String> hashtags;

    @Schema(description = "게시글에 달린 댓글들")
    private List<CommentDto> comments;

    @Schema(description = "작성일",example = "2023-07-06T07:23:32.16065")
    private LocalDateTime createdAt;
    @Schema(description = "수정일",example = "2023-07-06T07:23:32.16065")
    private LocalDateTime modifiedAt;

}
