package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.CreateBoardDto;
import com.lodestar.lodestar_server.dto.SaveBoardDto;
import com.lodestar.lodestar_server.entity.Board;
import com.lodestar.lodestar_server.entity.BoardHashtag;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.exception.AuthFailException;
import com.lodestar.lodestar_server.repository.BoardRepository;
import com.lodestar.lodestar_server.repository.HashtagRepository;
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
    private final HashtagRepository hashtagRepository;

    public void saveBoard(CreateBoardDto createBoardDto) {

        Board board = new Board();
        User user = userRepository.findById(createBoardDto.getUserId()).orElseThrow(() -> new AuthFailException(String.valueOf(createBoardDto.getUserId())));

        board.setUser(user);
        board.setTitle(createBoardDto.getTitle());
        board.setContent(createBoardDto.getContent());
        board.setQna(createBoardDto.getQna());
        //TODO: 커리어 이미지 y이면 이미지 저장, 아니면 n으로
        board.setCareerImage(createBoardDto.getShowGraph());

        List<BoardHashtag> hashtags = new ArrayList<>();
        List<String> hashtagNames = createBoardDto.getHashtags();

        for(int i = 0 ; i < hashtagNames.size(); i++) {
            BoardHashtag hashtag = new BoardHashtag();
            hashtag.setBoard(board);
            hashtag.setHashtagName(hashtagNames.get(i));
            hashtags.add(hashtag);
        }

        boardRepository.save(board);
        hashtagRepository.saveAll(hashtags);

    }

    public Long findAllBoard() {
        return boardRepository.findAllBoard().get(0).getId();
    }
}
