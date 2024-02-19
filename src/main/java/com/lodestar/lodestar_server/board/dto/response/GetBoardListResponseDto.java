package com.lodestar.lodestar_server.board.dto.response;

import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.career.dto.response.CareerResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@Schema(description = "메인 페이지 게시글 목록")
public class GetBoardListResponseDto {

    private List<BoardListDto> boards;


    static class BoardListDto {
        @Schema(description = "게시글 인덱스", example = "1")
        private Long boardId;
        @Schema(description = "게시글 제목", example = "안녕하세요. 초보 개발자입니다.")
        private String title;
        @Schema(description = "작성자")
        private String username;
        @Schema(description = "내용")
        private String content;
        @Schema(description = "북마크 수")
        private Integer bookmarkCount;
        @Schema(description = "조회수")
        private Integer view;
        @Schema(description = "게시글에 달린 해시태그들", example = "전공자, 현직자, React")
        private List<String> hashtags;
        @Schema(description = "게시글 작성자의 경력들")
        private List<CareerResponseDto> careerResponseDtos;
        @Schema(description = "작성 시간")
        private LocalDateTime createdAt;
        @Schema(description = "수정 시간")
        private LocalDateTime modifiedAt;

        @Builder
        private BoardListDto(Long boardId, String title, String username, String content, Integer bookmarkCount, Integer view, List<String> hashtags, List<CareerResponseDto> careerResponseDtos, LocalDateTime createdAt, LocalDateTime modifiedAt) {
            this.boardId = boardId;
            this.title = title;
            this.username = username;
            this.content = content;
            this.bookmarkCount = bookmarkCount;
            this.view = view;
            this.hashtags = hashtags;
            this.careerResponseDtos = careerResponseDtos;
            this.createdAt = createdAt;
            this.modifiedAt = modifiedAt;
        }

        public static BoardListDto of(Board board) {
            return BoardListDto.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .view(board.getView())
                    .bookmarkCount(board.getBookmarkCount())
                    .hashtags(board.getHashtags().stream()
                            .map(hashtag -> hashtag.getId().getHashtagName())
                            .collect(Collectors.toList()))
                    .careerResponseDtos(board.getUser().getCareers().stream()
                            .map(career -> CareerResponseDto.of(career))
                            .collect(Collectors.toList()))
                    .username(board.getUser().getUsername())
                    .createdAt(board.getCreatedAt())
                    .modifiedAt(board.getModifiedAt())
                    .build();
        }
    }


    @Builder
    private GetBoardListResponseDto(List<BoardListDto> boardListDtoList) {
        this.boards = boardListDtoList;
    }

    public static GetBoardListResponseDto of(List<Board> boards) {
        return GetBoardListResponseDto.builder()
                .boards(boards.stream()
                        .map(board -> BoardListDto.of(board))
                        .collect(Collectors.toList()))
                .build();
    }
}
