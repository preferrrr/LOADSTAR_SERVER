package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.SaveBookmarkDto;
import com.lodestar.lodestar_server.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/new")
    public ResponseEntity<?> saveBookmark(@RequestBody SaveBookmarkDto saveBookmarkDto) {
        saveBookmarkDto.validateFieldsNotNull();
        bookmarkService.saveBookmark(saveBookmarkDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{boardId}")
    public ResponseEntity<?> deleteBookmark(@PathVariable("userId") Long userId, @PathVariable("boardId") Long boardId) {

        bookmarkService.deleteBookmark(userId, boardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
