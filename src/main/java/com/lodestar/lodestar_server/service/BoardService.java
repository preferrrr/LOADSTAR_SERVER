package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.SaveBoardDto;
import com.lodestar.lodestar_server.entity.Board;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.repository.BoardRepository;
import com.lodestar.lodestar_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Long saveBoard(SaveBoardDto saveBoardDto) {
        Board newBoard = new Board();
        newBoard.setTitle(saveBoardDto.getTitle());
        newBoard.setContent(saveBoardDto.getContent());
        User user = new User();
        user.setUsername("board test2");
        user.setEmail("board test2");
        user.setPassword("board test2");
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);
        user.setRefreshTokenValue(null);
        User expect = userRepository.save(user);
        newBoard.setUser(expect);
        boardRepository.save(newBoard);
        return newBoard.getId();
    }

    public Long findAllBoard() {
        return boardRepository.findAllBoard().get(0).getId();
    }
}
