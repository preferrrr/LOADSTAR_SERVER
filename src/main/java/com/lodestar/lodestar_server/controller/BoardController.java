package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.BoardPagingDto;
import com.lodestar.lodestar_server.dto.CreateBoardDto;
import com.lodestar.lodestar_server.dto.SaveBoardDto;
import com.lodestar.lodestar_server.jwt.JwtProvider;
import com.lodestar.lodestar_server.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/new")
    public ResponseEntity<?> saveBoard(@RequestBody CreateBoardDto createBoardDto) {

        createBoardDto.validateFieldsNotNull();
        boardService.saveBoard(createBoardDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/main")
    public ResponseEntity<?> main(Pageable pageable,
                                  @RequestParam(name = "page", defaultValue = "0") int page) {
        List<BoardPagingDto> response= boardService.main(pageable, page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
