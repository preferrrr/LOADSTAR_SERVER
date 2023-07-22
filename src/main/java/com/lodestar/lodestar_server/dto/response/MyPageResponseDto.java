package com.lodestar.lodestar_server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResponseDto {

    private String username;
    private String email;
    private ArrayList<MyBoardDto> boards;
    private ArrayList<BookmarkDto> bookmarks;
}
