package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.BoardPagingDto;
import com.lodestar.lodestar_server.dto.CreateBoardDto;
import com.lodestar.lodestar_server.dto.GetBoardResponseDto;
import com.lodestar.lodestar_server.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**게시글 작성*/
    @PostMapping("/new")
    public ResponseEntity<?> saveBoard(@RequestBody CreateBoardDto createBoardDto) {

        createBoardDto.validateFieldsNotNull();
        boardService.saveBoard(createBoardDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**메인페이지 게시글 조회*/
    @GetMapping(value = "/main")
    public ResponseEntity<?> getBoardList(@PageableDefault(size = 8, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                  @RequestParam("hashtags") String[] hashtags) {

        List<BoardPagingDto> response= boardService.getBoardList(pageable, hashtags);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable("boardId") Long boardId, @PathVariable("userId") Long userId) {

        GetBoardResponseDto responseDto = boardService.getBoard(userId, boardId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }








    @PostMapping("/new2")
    public ResponseEntity<?> saveBoard100(@RequestBody CreateBoardDto createBoardDto) {

        createBoardDto.validateFieldsNotNull();
        boardService.saveBoard100(createBoardDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
