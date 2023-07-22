package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.request.CreateBoardDto;
import com.lodestar.lodestar_server.dto.request.ModifyBoardDto;
import com.lodestar.lodestar_server.dto.response.BoardPagingDto;
import com.lodestar.lodestar_server.dto.response.GetBoardResponseDto;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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


    /**메인페이지 게시글 조회
     * /boards
     * */
    @GetMapping(value = "")
    @Operation(summary = "메인 페이지 게시글 목록 조회")
    @ApiResponse(responseCode = "200", description = "성공",
            content = {@Content(array = @ArraySchema(schema = @Schema(implementation = BoardPagingDto.class)))} )
    public ResponseEntity<?> getBoardList(@PageableDefault(size = 8, sort = "createdAt", direction = Sort.Direction.DESC)
                                              @Schema(description = "페이지 번호", example = "0") Pageable pageable,
                                          @RequestParam("hashtags") @Schema(description = "적용할 해시태그들",example = "알고리즘, 운영체제") String[] hashtags) {

        List<BoardPagingDto> response= boardService.getBoardList(pageable, hashtags);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**게시글 작성
     * /boards
     * */
    @PostMapping("")
    @Operation(summary = "게시글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
            @ApiResponse(responseCode = "403", description = "권한없음, 토큰 만료")
    })
    public ResponseEntity<?> saveBoard(@AuthenticationPrincipal User user,
                                       @RequestBody CreateBoardDto createBoardDto) {

        createBoardDto.validateFieldsNotNull();
        boardService.saveBoard(user, createBoardDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }




    /**
     * 게시글 인덱스로 조회
     * /boards/{boardId}
     * */
    @GetMapping(value = "/{boardId}")
    @Operation(summary = "게시글 인덱스로 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = GetBoardResponseDto.class)))
    })
    public ResponseEntity<?> getBoard(@AuthenticationPrincipal User user,
                                      @Schema(description = "게시글 인덱스", example = "1") @PathVariable("boardId") Long boardId) {

        GetBoardResponseDto responseDto = boardService.getBoard(user, boardId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }




    /**
     * 게시글 삭제
     * /boards/{boardId}
     * */
    @DeleteMapping(value = "/{boardId}")
    @Operation(summary = "게시글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    public ResponseEntity<?> deleteBoard(@AuthenticationPrincipal User user,
                                         @Schema(description = "게시글 인덱스", example = "1") @PathVariable("boardId") Long boardId) {

        boardService.deleteBoard(user, boardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 게시글 수정
     * /boards/{boardId}
     * */
    @PatchMapping(value = "/{boardId}")
    @Operation(summary = "게시글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    public ResponseEntity<?> modifyBoard(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId,
                                         @RequestBody ModifyBoardDto modifyBoardDto) {

        modifyBoardDto.validateFieldsNotNull();
        boardService.modifyBoard(user, boardId, modifyBoardDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

//
//    @PostMapping(value = "/image")
//    public ResponseEntity<?> imageTest(@RequestPart(value = "file", required = false)MultipartFile file,
//                                       @RequestPart(value = "data") TestDto testDto) throws Exception {
//
//
//        //게시글 작성.
//        if (file.isEmpty()) {
//            //상품 등록시 첫 번쨰 이미지가 없다면 에러 메시지와 함께 상품 등록 페이지로 전환
//            //상품의 첫 번째 이미지는 메인 페이지에서 보ㅕ줄 상품 이미지로 사용하기 위해서 필수 값으로 지정
//        }
//
//        return new ResponseEntity<>(boardService.imageTest(testDto, file), HttpStatus.OK);
//
//    }



//
//    @PostMapping("/new2")
//    public ResponseEntity<?> saveBoard100(@RequestBody CreateBoardDto createBoardDto) {
//
//        createBoardDto.validateFieldsNotNull();
//        boardService.saveBoard100(createBoardDto);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
