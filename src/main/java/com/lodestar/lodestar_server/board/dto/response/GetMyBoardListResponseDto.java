package com.lodestar.lodestar_server.board.dto.response;

import com.lodestar.lodestar_server.board.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetMyBoardListResponseDto {

    private List<MyBoardDto> myBoards;

    @Builder
    private GetMyBoardListResponseDto(List<MyBoardDto> myBoards) {
        this.myBoards = myBoards;
    }

    public static GetMyBoardListResponseDto of(List<Board> boards) {
        return GetMyBoardListResponseDto.builder()
                .myBoards(boards.stream()
                        .map(board -> MyBoardDto.of(board))
                        .collect(Collectors.toList()))
                .build();
    }

    static class MyBoardDto {
        private Long boardId;
        private String title;
        private Integer bookmarkCount;
        private Integer view;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        @Builder
        private MyBoardDto(Long boardId, String title, Integer bookmarkCount, Integer view, LocalDateTime createdAt, LocalDateTime modifiedAt) {
            this.boardId = boardId;
            this.title = title;
            this.bookmarkCount = bookmarkCount;
            this.view = view;
            this.createdAt = createdAt;
            this.modifiedAt = modifiedAt;
        }

        public static MyBoardDto of(Board board) {
            return MyBoardDto.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .bookmarkCount(board.getBookmarkCount())
                    .view(board.getView())
                    .createdAt(board.getCreatedAt())
                    .modifiedAt(board.getModifiedAt())
                    .build();
        }
    }
}
