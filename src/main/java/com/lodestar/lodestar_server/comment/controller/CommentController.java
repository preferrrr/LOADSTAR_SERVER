package com.lodestar.lodestar_server.comment.controller;

import com.lodestar.lodestar_server.comment.dto.request.CreateCommentDto;
import com.lodestar.lodestar_server.comment.dto.request.ModifyCommentDto;
import com.lodestar.lodestar_server.comment.dto.response.MyCommentResponseDto;
import com.lodestar.lodestar_server.user.entity.User;
import com.lodestar.lodestar_server.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity saveComment(@AuthenticationPrincipal User user,
                                      @RequestBody @Valid CreateCommentDto createCommentDto) {


        commentService.createComment(user, createCommentDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 댓글 삭제
     * /comments/{commentId}
     */
    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    public ResponseEntity deleteComment(@AuthenticationPrincipal User user,
                                        @Schema(name = "댓글 id") @PathVariable("commentId") Long commentId) {

        commentService.deleteComment(user, commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 댓글 수정
     * /comments/{commentId}
     */

    @PatchMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    public ResponseEntity modifyComment(@AuthenticationPrincipal User user,
                                        @Schema(name = "댓글 id") @PathVariable("commentId") Long commentId,
                                        @RequestBody @Valid ModifyCommentDto modifyCommentDto) {


        commentService.modifyComment(user, commentId, modifyCommentDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 내가 쓴 댓글 조회
     * /comments/my-comments
     */
    @GetMapping(value = "/my-comments")
    @Operation(summary = "내가 쓴 댓글 조회")
    @ApiResponse(responseCode = "200", description = "성공",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = MyCommentResponseDto.class)))})
    public ResponseEntity<MyCommentResponseDto> getMyComments(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                              @AuthenticationPrincipal User user) {

        return new ResponseEntity<>(commentService.getMyComments(user, pageable), HttpStatus.OK);
    }
}
