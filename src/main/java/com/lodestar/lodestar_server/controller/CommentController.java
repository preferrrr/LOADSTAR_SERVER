package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.CreateCommentDto;
import com.lodestar.lodestar_server.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/new")
    public ResponseEntity<?> saveComment(@RequestBody CreateCommentDto createCommentDto) {

        createCommentDto.validateFieldsNotNull();

        commentService.createComment(createCommentDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
