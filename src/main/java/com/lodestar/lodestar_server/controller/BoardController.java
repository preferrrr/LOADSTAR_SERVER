package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.*;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/boards")
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;


    /**메인페이지 게시글 조회
     * /boards
     * */
    @GetMapping(value = "")
    public ResponseEntity<?> getBoardList(@PageableDefault(size = 8, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                          @RequestParam("hashtags") String[] hashtags) {

        List<BoardPagingDto> response= boardService.getBoardList(pageable, hashtags);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**게시글 작성
     * /boards
     * */
    @PostMapping("")
    public ResponseEntity<?> saveBoard(@AuthenticationPrincipal User user, @RequestBody CreateBoardDto createBoardDto) {

        System.out.println("controller, userId: " + user.getId());
        createBoardDto.validateFieldsNotNull();
        boardService.saveBoard(user, createBoardDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }




    /**
     * 게시글 인덱스로 조회
     * /boards/{boardId}
     * */
    @GetMapping(value = "/{boardId}")
    public ResponseEntity<?> getBoard(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId) {

        GetBoardResponseDto responseDto = boardService.getBoard(user, boardId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }




    /**
     * 게시글 삭제
     * /boards/{boardId}
     * */
    @DeleteMapping(value = "/{boardId}")
    public ResponseEntity<?> deleteBoard(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId) {

        boardService.deleteBoard(user, boardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 게시글 수정
     * /boards/{boardId}
     * */
    @PatchMapping(value = "/{boardId}")
    public ResponseEntity<?> modifyBoard(@AuthenticationPrincipal User user, @PathVariable("boardId") Long boardId,
                                         @RequestBody ModifyBoardDto modifyBoardDto) {

        modifyBoardDto.validateFieldsNotNull();
        boardService.modifyBoard(user, boardId, modifyBoardDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(value = "/image")
    public ResponseEntity<?> imageTest(@RequestPart(value = "file", required = false)MultipartFile file,
                                       @RequestPart(value = "data") TestDto testDto) throws Exception {


        //게시글 작성.
        if (file.isEmpty()) {
            //상품 등록시 첫 번쨰 이미지가 없다면 에러 메시지와 함께 상품 등록 페이지로 전환
            //상품의 첫 번째 이미지는 메인 페이지에서 보ㅕ줄 상품 이미지로 사용하기 위해서 필수 값으로 지정
        }

        return new ResponseEntity<>(boardService.imageTest(testDto, file), HttpStatus.OK);

    }



//
//    @PostMapping("/new2")
//    public ResponseEntity<?> saveBoard100(@RequestBody CreateBoardDto createBoardDto) {
//
//        createBoardDto.validateFieldsNotNull();
//        boardService.saveBoard100(createBoardDto);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    //git test
}
