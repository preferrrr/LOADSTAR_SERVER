package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.BoardPagingDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
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

    public List<BoardPagingDto> main(Pageable pageable, int page) {
        pageable = PageRequest.of(page,4);

        Page<Board> boards = boardRepository.findAll(pageable);
        List<BoardPagingDto> list = new ArrayList<>();

        for (Board board : boards) {
            BoardPagingDto dto = new BoardPagingDto();
            dto.setBoardId(board.getId());
            dto.setTitle(board.getTitle());
            dto.setQna(board.getQna());
            dto.setCareerImage(board.getCareerImage());
            List<String> hashtagNames = new ArrayList<>();

            for (BoardHashtag hashtag : board.getHashtag()) {
                hashtagNames.add(hashtag.getHashtagName());
            }

            dto.setHashtags(hashtagNames);

            System.out.println(board.getHashtag());
            list.add(dto);
        }

        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getTitle());
        }
        return list;
    }



}
