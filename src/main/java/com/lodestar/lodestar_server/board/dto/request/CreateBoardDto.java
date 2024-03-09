package com.lodestar.lodestar_server.board.dto.request;

import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "게시글 작성 body")
@AllArgsConstructor
public class CreateBoardDto {

    @Schema(description = "게시글 제목", example = "안녕하세요.")
    @NotBlank(message = "제목은 null 또는 공백일 수 없습니다.")
    private String title;

    @Schema(description = "게시글 내용", example = "이제 뭐를 더 공부하면 좋을까요 ?")
    @NotBlank(message = "내용은 null 또는 공백일 수 없습니다.")
    private String content;
    
    @Schema(description = "게시글에 태그할 해시태그들", example = "알고리즘, 운영체제")
    private List<String> hashtags = new ArrayList<>();

}
