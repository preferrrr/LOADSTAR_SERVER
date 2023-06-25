package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.CreateBoardDto;
import com.lodestar.lodestar_server.dto.SaveBoardDto;
import com.lodestar.lodestar_server.jwt.JwtProvider;
import com.lodestar.lodestar_server.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
@AllArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final JwtProvider jwtProvider;

    @GetMapping("/select")
    public ResponseEntity<?> findAllBoard() {
        return new ResponseEntity<>(boardService.findAllBoard(), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> saveBoard(@RequestBody CreateBoardDto createBoardDto) {

        createBoardDto.validateFieldsNotNull();
        boardService.saveBoard(createBoardDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
