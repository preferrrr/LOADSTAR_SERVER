package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.BoardPagingDto;
import com.lodestar.lodestar_server.entity.Board;
import com.lodestar.lodestar_server.exception.AuthFailException;
import com.lodestar.lodestar_server.repository.BoardRepository;
import com.lodestar.lodestar_server.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final BoardService boardService;

    @GetMapping(value = "/")
    public ResponseEntity<?> main(Pageable pageable,
            @RequestParam(name = "page", defaultValue = "0") int page) {
        List<BoardPagingDto> response= boardService.main(pageable, page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
