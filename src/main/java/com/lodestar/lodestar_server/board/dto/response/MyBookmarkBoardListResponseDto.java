package com.lodestar.lodestar_server.board.dto.response;

import com.lodestar.lodestar_server.board.entity.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MyBookmarkBoardListResponseDto {

    private List<MyBookmarkBoardDto> boards;

    @Builder
    private MyBookmarkBoardListResponseDto(List<MyBookmarkBoardDto> boards) {
        this.boards = boards;
    }

    public static MyBookmarkBoardListResponseDto of(List<Board> boards) {
        return MyBookmarkBoardListResponseDto.builder()
                .boards(boards.stream()
                        .map(board -> MyBookmarkBoardDto.of(board))
                        .collect(Collectors.toList()))
                .build();
    }

    static class MyBookmarkBoardDto {
        @Schema(description = "게시글 id")
        private Long boardId;
        @Schema(description = "게시글 제목")
        private String title;
        @Schema(description = "유저 아이디")
        private String username;
        @Schema(description = "게시글 작성 시간")
        private LocalDateTime createdAt;
        @Schema(description = "게시글 수정 시간")
        private LocalDateTime modifiedAt;
        @Schema(description = "북마크 수")
        private Integer bookmarkCount;
        @Schema(description = "조회수")
        private Integer view;

        @Builder
        private MyBookmarkBoardDto(Long boardId, String title, String username, LocalDateTime createdAt, LocalDateTime modifiedAt, Integer bookmarkCount, Integer view) {
            this.boardId = boardId;
            this.title = title;
            this.username = username;
            this.createdAt = createdAt;
            this.modifiedAt = modifiedAt;
            this.bookmarkCount = bookmarkCount;
            this.view = view;
        }

        public static MyBookmarkBoardDto of(Board board) {
            return MyBookmarkBoardDto.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .username(board.getUser().getUsername())
                    .bookmarkCount(board.getBookmarkCount())
                    .view(board.getView())
                    .createdAt(board.getCreatedAt())
                    .modifiedAt(board.getModifiedAt())
                    .build();
        }
    }


}
