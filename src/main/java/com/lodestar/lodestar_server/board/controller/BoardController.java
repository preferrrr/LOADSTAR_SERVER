package com.lodestar.lodestar_server.board.controller;

import com.lodestar.lodestar_server.board.dto.request.CreateBoardDto;
import com.lodestar.lodestar_server.board.dto.request.ModifyBoardDto;
import com.lodestar.lodestar_server.board.dto.response.GetBoardListDto;
import com.lodestar.lodestar_server.board.dto.response.GetBoardResponseDto;
import com.lodestar.lodestar_server.board.dto.response.MyBoardDto;
import com.lodestar.lodestar_server.board.dto.response.MyBookmarkBoardDto;
import com.lodestar.lodestar_server.user.entity.User;
import com.lodestar.lodestar_server.exception.InvalidRequestParameterException;
import com.lodestar.lodestar_server.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
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
@RequestMapping("/boards")
@AllArgsConstructor
@Tag(name = "Board", description = "게시글 관련 API")
public class BoardController {

    private final BoardService boardService;


    /**
     * 메인페이지 게시글 조회
     * /boards
     */
    @GetMapping(value = "")
    @Operation(summary = "메인 페이지 게시글 목록 조회")
    @ApiResponse(responseCode = "200", description = "성공",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = GetBoardListDto.class)))})
    public ResponseEntity<List<GetBoardListDto>> getBoardList(@Schema(description = "페이징처리. createdAt,view,bookmarkCount / desc,asc",
            example = "createdAt,desc / view,asc / bookmarkCount,desc")
                                          @PageableDefault(size = 9, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                              @Schema(description = "적용할 해시태그들",example = "알고리즘, 운영체제") @RequestParam(value = "hashtags", required = false) String[] hashtags) {

        List<GetBoardListDto> response = boardService.getBoardList(pageable, hashtags);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 게시글 작성
     * /boards
     */
    @PostMapping("")
    @Operation(summary = "게시글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
            @ApiResponse(responseCode = "401", description = "세션 만료")
    })
    public ResponseEntity saveBoard(@AuthenticationPrincipal User user,
                                       @RequestBody CreateBoardDto createBoardDto) {

        createBoardDto.validateFieldsNotNull();
        boardService.saveBoard(user, createBoardDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /**
     * 게시글 인덱스로 조회
     * /boards/{boardId}
     */
    @GetMapping(value = "/{boardId}")
    @Operation(summary = "게시글 인덱스로 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = GetBoardResponseDto.class)))
    })
    public ResponseEntity<GetBoardResponseDto> getBoard(HttpSession httpSession,
                                      @AuthenticationPrincipal User user,
                                      @Schema(description = "게시글 인덱스", example = "1") @PathVariable("boardId") Long boardId) {

        GetBoardResponseDto responseDto = boardService.getBoard(httpSession, user, boardId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    /**
     * 게시글 삭제
     * /boards/{boardId}
     */
    @DeleteMapping(value = "/{boardId}")
    @Operation(summary = "게시글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    public ResponseEntity deleteBoard(@AuthenticationPrincipal User user,
                                         @Schema(description = "게시글 인덱스", example = "1") @PathVariable("boardId") Long boardId) {

        boardService.deleteBoard(user, boardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 게시글 수정
     * /boards/{boardId}
     */
    @PatchMapping(value = "/{boardId}")
    @Operation(summary = "게시글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    public ResponseEntity modifyBoard(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId,
                                         @RequestBody ModifyBoardDto modifyBoardDto) {

        modifyBoardDto.validateFieldsNotNull();
        boardService.modifyBoard(user, boardId, modifyBoardDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 내가 쓴 게시글 조회
     * /boards/my-boards
     */
    @GetMapping(value = "/my-boards")
    @Operation(summary = "내가 쓴 게시글 조회")
    @ApiResponse(responseCode = "200", description = "성공",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = MyBoardDto.class)))})
    public ResponseEntity<List<MyBoardDto>> getMyBoardList(@Schema(description = "페이징처리. createdAt,view,bookmarkCount / desc,asc",
            example = "createdAt,desc / view,asc / bookmarkCount,desc")
                                          @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                          @AuthenticationPrincipal User user) {

        List<MyBoardDto> response = boardService.getMyBoardList(user, pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 북마크한 게시글 조회
     * /boards/my-boards
     */
    @GetMapping(value = "/my-bookmarks")
    @Operation(summary = "북마크한 조회")
    @ApiResponse(responseCode = "200", description = "성공",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = MyBookmarkBoardDto.class)))})
    public ResponseEntity<List<MyBookmarkBoardDto>> getMyBookmarkBoardList(@Schema(description = "페이징처리. createdAt,view,bookmarkCount / desc,asc",
            example = "createdAt,desc / view,asc / bookmarkCount,desc")
                                            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                            @AuthenticationPrincipal User user) {

        List<MyBookmarkBoardDto> response = boardService.getMyBookmarkBoardList(user, pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 댓글 작성한 게시글 조회
     * /boards/my-boards
     */
    @GetMapping(value = "/my-comment-boards")
    @Operation(summary = "댓글 작성한 게시글 조회")
    @ApiResponse(responseCode = "200", description = "성공",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = GetBoardListDto.class)))})
    public ResponseEntity<List<GetBoardListDto>> getMyCommentBoardList(@Schema(description = "페이징처리. createdAt,view,bookmarkCount / desc,asc",
            example = "createdAt,desc / view,asc / bookmarkCount,desc")
                                                    @PageableDefault(size = 9, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                       @AuthenticationPrincipal User user) {

        List<GetBoardListDto> response = boardService.getMyCommentBoardList(user, pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 게시글 검색
     * /boards/search
     */
    @GetMapping(value = "/search")
    @Operation(summary = "게시글 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = GetBoardListDto.class)))}),
            @ApiResponse(responseCode = "204", description = "body null 존재")
    })
    public ResponseEntity<List<GetBoardListDto>> searchBoards(@PageableDefault(size = 9, sort = "created_at", direction = Sort.Direction.DESC)
                                          @Schema(description = "페이지 번호", example = "0") Pageable pageable,
                                                              @Schema(description = "검색 키워드", example = "스프링 부트") @RequestParam("keywords") String keywords) {

        if (keywords.length() == 0 || keywords.isEmpty() || keywords.isBlank())
            throw new InvalidRequestParameterException("invalid keywords"); // 검색어가 없을 때 204 NO CONTENT 반환.

        List<GetBoardListDto> response = boardService.searchBoards(pageable, keywords);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
