package com.lodestar.lodestar_server.dto;

import com.lodestar.lodestar_server.entity.BoardHashtag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "메인 페이지 게시글 목록")
public class BoardPagingDto {

    @Schema(description = "게시글 인덱스", example = "1")
    private Long boardId;
    @Schema(description = "게시글 제목", example = "안녕하세요. 초보 개발자입니다.")
    private String title;
    @Schema(description = "게시글에 달린 해시태그들", example = "전공자, 현직자, React")
    private List<String> hashtags;
    @Schema(description = "게시글 작성자의 경력들")
    private List<CareerDto> arr;
}