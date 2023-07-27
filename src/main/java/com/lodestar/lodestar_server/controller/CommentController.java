package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.request.CreateCommentDto;
import com.lodestar.lodestar_server.dto.request.ModifyCommentDto;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "댓글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
    })
    public ResponseEntity<?> saveComment(@AuthenticationPrincipal User user,
                                         @RequestBody CreateCommentDto createCommentDto) {

        createCommentDto.validateFieldsNotNull();

        commentService.createComment(user, createCommentDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     * /comments/{commentId}
     * */
    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal User user,
                                           @Schema(name = "댓글 id") @PathVariable("commentId") Long commentId) {

        commentService.deleteComment(user, commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 댓글 수정
     * /comments/{commentId}
     * */

    @PatchMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    public ResponseEntity<?> modifyComment(@AuthenticationPrincipal User user,
                                           @Schema(name = "댓글 id") @PathVariable("commentId") Long commentId,
                                           @RequestBody ModifyCommentDto modifyCommentDto) {

        modifyCommentDto.validateFieldsNotNull();

        commentService.modifyComment(user, commentId, modifyCommentDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    //TODO: 내가 쓴 댓글 조회
}
