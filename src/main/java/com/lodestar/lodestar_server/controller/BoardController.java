package com.lodestar.lodestar_server.controller;

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

    @PostMapping("/create")
    public ResponseEntity<?> saveBoard(@RequestHeader("Authorization") String headers,
                                       @RequestBody SaveBoardDto saveBoardDto) {

        String token = headers;
        System.out.println("##################################"+token);

        String userId = jwtProvider.getUserId(token);
        System.out.println(userId);

        boardService.saveBoard(saveBoardDto);
        return new ResponseEntity<>("test", HttpStatus.OK);
    }

    @GetMapping("/select")
    public ResponseEntity<?> findAllBoard() {
        return new ResponseEntity<>(boardService.findAllBoard(), HttpStatus.OK);
    }
}
