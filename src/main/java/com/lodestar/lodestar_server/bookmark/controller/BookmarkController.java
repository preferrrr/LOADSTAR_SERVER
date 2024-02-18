package com.lodestar.lodestar_server.bookmark.controller;

import com.lodestar.lodestar_server.bookmark.dto.request.SaveBookmarkDto;
import com.lodestar.lodestar_server.user.entity.User;
import com.lodestar.lodestar_server.bookmark.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    /**북마크 등록
     * /bookmarks
     * */
    @PostMapping("")
    @Operation(summary = "북마크 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
    })
    public ResponseEntity saveBookmark(@AuthenticationPrincipal User user, @RequestBody SaveBookmarkDto saveBookmarkDto) {
        saveBookmarkDto.validateFieldsNotNull();
        bookmarkService.saveBookmark(user, saveBookmarkDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 북마크 삭제
     * /bookmarks
     * */
    @DeleteMapping("/{boardId}")
    @Operation(summary = "북마크 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity deleteBookmark(@AuthenticationPrincipal User user,
                                            @Schema(description = "게시글 인덱스", example = "1") @PathVariable("boardId") Long boardId) {

        bookmarkService.deleteBookmark(user, boardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
