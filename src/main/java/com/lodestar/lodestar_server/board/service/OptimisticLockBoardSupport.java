package com.lodestar.lodestar_server.board.service;

import com.lodestar.lodestar_server.board.dto.response.GetBoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OptimisticLockBoardSupport {

    private final BoardService boardService;

    public GetBoardResponseDto getBoard(Long boardId) throws InterruptedException {

        while (true) {
            try {
                return boardService.getBoard(boardId);
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
