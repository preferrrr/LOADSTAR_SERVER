package com.lodestar.lodestar_server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "마이페이지 응답")
public class MyPageResponseDto {

    @Schema(name = "유저 아이디")
    private String username;
    @Schema(name = "유저 이메일")
    private String email;
    @Schema(name = "내가 쓴 게시글 리스트")
    private ArrayList<MyBoardDto> boards;
    @Schema(name = "북마크한 게시글 리스트")
    private ArrayList<BookmarkDto> bookmarks;
    @Schema(name = "내가 쓴 댓글 리스트")
    private ArrayList<MyCommentDto> comments;
}
