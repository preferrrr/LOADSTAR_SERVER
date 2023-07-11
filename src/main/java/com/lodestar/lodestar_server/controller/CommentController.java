package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.CreateCommentDto;
import com.lodestar.lodestar_server.dto.ModifyCommentDto;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     * /comments
     */
    @PostMapping("")
    public ResponseEntity<?> saveComment(@AuthenticationPrincipal User user, @RequestBody CreateCommentDto createCommentDto) {

        createCommentDto.validateFieldsNotNull();

        commentService.createComment(user, createCommentDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     * /comments/{commentId}
     * */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal User user, @PathVariable("commentId") Long commentId) {

        commentService.deleteComment(user, commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 댓글 수정
     * /comments/{commentId}
     * */

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> modifyComment(@AuthenticationPrincipal User user, @PathVariable("commentId") Long commentId,
                                           @RequestBody ModifyCommentDto modifyCommentDto) {

        modifyCommentDto.validateFieldsNotNull();

        commentService.modifyComment(user, commentId, modifyCommentDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    //TODO: 내가 쓴 댓글 조회
}
