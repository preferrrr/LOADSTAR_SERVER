package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.SaveBookmarkDto;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.service.BookmarkService;
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

    //북마크 등록

    @PostMapping("")
    public ResponseEntity<?> saveBookmark(@AuthenticationPrincipal User user, @RequestBody SaveBookmarkDto saveBookmarkDto) {
        saveBookmarkDto.validateFieldsNotNull();
        bookmarkService.saveBookmark(user, saveBookmarkDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBookmark(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId) {

        bookmarkService.deleteBookmark(user, boardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
