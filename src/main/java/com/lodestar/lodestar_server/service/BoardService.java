package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.BoardPagingDto;
import com.lodestar.lodestar_server.dto.CreateBoardDto;
import com.lodestar.lodestar_server.dto.GetBoardResponseDto;
import com.lodestar.lodestar_server.dto.GetCommentResponseDto;
import com.lodestar.lodestar_server.entity.Board;
import com.lodestar.lodestar_server.entity.BoardHashtag;
import com.lodestar.lodestar_server.entity.Comment;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.exception.AuthFailException;
import com.lodestar.lodestar_server.repository.BoardRepository;
import com.lodestar.lodestar_server.repository.HashtagRepository;
import com.lodestar.lodestar_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
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
        //TODO: 커리어 이미지 y이면 이미지 저장, 아니면 n으로
        board.setCareerImage(createBoardDto.getShowGraph());

        List<BoardHashtag> hashtags = new ArrayList<>();
        List<String> hashtagNames = createBoardDto.getHashtags();

        for (int i = 0; i < hashtagNames.size(); i++) {
            BoardHashtag hashtag = new BoardHashtag();
            hashtag.setBoard(board);
            hashtag.setHashtagName(hashtagNames.get(i));
            hashtags.add(hashtag);
        }

        BoardHashtag current = new BoardHashtag();
        current.setBoard(board);
        if (user.getCurrent().equals("y")) {
            current.setHashtagName("현직자");
        } else if (user.getCurrent().equals("n")) {
            current.setHashtagName("비현직자");
        }
        hashtags.add(current);

        BoardHashtag major = new BoardHashtag();
        major.setBoard(board);
        if (user.getCurrent().equals("y")) {
            major.setHashtagName("전공자");
        } else if (user.getCurrent().equals("n")) {
            major.setHashtagName("비전공자");
        }
        hashtags.add(major);

        BoardHashtag frontBack = new BoardHashtag();
        frontBack.setBoard(board);
        if (user.getCurrent().equals("y")) {
            frontBack.setHashtagName("front");
        } else if (user.getCurrent().equals("n")) {
            frontBack.setHashtagName("back");
        }
        hashtags.add(frontBack);


        boardRepository.save(board);
        hashtagRepository.saveAll(hashtags);

    }



    @Transactional(readOnly = true)
    public List<BoardPagingDto> getBoardList(Pageable pageable, String[] hashtags) {

        List<Board> boards = new ArrayList<>();
        List<BoardPagingDto> result = new ArrayList<>();

        if(hashtags.length == 0) {
            Page<Board> pageBoards = boardRepository.findAll(pageable);

            for (Board board : pageBoards) {
                boards.add(board);
            }
        }
        else {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "created_at");


            List<String> hashtagList = new ArrayList<>();

            for (String item : hashtags) {
                hashtagList.add(item);
            }

            Page<Long> pagingIds = boardRepository.findBoardIdByHashtags(pageable, hashtagList);


            //TODO: native query를 사용해서 board_id만 조회한 이유

            List<Long> boardIds = new ArrayList<>();
            for (Long id : pagingIds) {
                boardIds.add(id);
            }

            System.out.println(boardIds);

            boards = boardRepository.findBoardsWhereInBoardIds(boardIds);

        }


        for (Board board : boards) {
            BoardPagingDto dto = new BoardPagingDto();
            dto.setBoardId(board.getId());
            dto.setTitle(board.getTitle());
            dto.setCareerImage(board.getCareerImage());
            List<String> hashtagNames = new ArrayList<>();

            for (BoardHashtag hashtag : board.getHashtag()) {
                hashtagNames.add(hashtag.getHashtagName());
            }
            dto.setHashtags(hashtagNames);

            result.add(dto);
        }

        return result;
    }


    @Transactional(readOnly = true)
    public GetBoardResponseDto getBoard(Long boardId) {
        Board findBoard = boardRepository.findByPathBoardId(boardId);

        GetBoardResponseDto response = new GetBoardResponseDto();

        response.setBoardId(findBoard.getId());
        response.setTitle(findBoard.getTitle());
        response.setContent(findBoard.getContent());
        response.setCreatedAt(findBoard.getCreatedAt());
        response.setModifiedAt(findBoard.getModifiedAt());
        response.setUserId(findBoard.getUser().getId());
        response.setCareerImage(findBoard.getCareerImage());

        List<BoardHashtag> hashtagList = findBoard.getHashtag();
        List<String> hashtags = new ArrayList<>();
        for (BoardHashtag hashtag : hashtagList) {
            hashtags.add(hashtag.getHashtagName());
        }
        response.setHashtags(hashtags);

        List<Comment> commentList = findBoard.getComments();
        List<GetCommentResponseDto> comments = new ArrayList<>();

        for(Comment comment : commentList) {
            GetCommentResponseDto commentDto = new GetCommentResponseDto();

            commentDto.setCommentId(comment.getId());
            commentDto.setCommentContent(comment.getContent());
            commentDto.setUserId(comment.getUser().getId());
            commentDto.setCreatedAt(comment.getCreatedAt());
            commentDto.setModifiedAt(comment.getModifiedAt());

            comments.add(commentDto);
        }

        response.setComments(comments);

        return response;
    }







    public void saveBoard100(CreateBoardDto createBoardDto) {
        User user = userRepository.findById(createBoardDto.getUserId()).orElseThrow(() -> new AuthFailException(String.valueOf(createBoardDto.getUserId())));

        for (int j = 0; j < 100; j++) {
            Board board = new Board();

            board.setUser(user);
            board.setTitle("test " + j);
            board.setContent(createBoardDto.getContent());
            //TODO: 커리어 이미지 y이면 이미지 저장, 아니면 n으로
            board.setCareerImage(createBoardDto.getShowGraph());

            List<BoardHashtag> hashtags = new ArrayList<>();
            List<String> hashtagNames = createBoardDto.getHashtags();

            for (int i = 0; i < hashtagNames.size(); i++) {
                BoardHashtag hashtag = new BoardHashtag();
                hashtag.setBoard(board);
                hashtag.setHashtagName(hashtagNames.get(i));
                hashtags.add(hashtag);
            }

            boardRepository.save(board);
            hashtagRepository.saveAll(hashtags);
        }
    }



    //
//    @Transactional(readOnly = true)
//    public List<BoardPagingDto> main(Pageable pageable) {
//
//        Page<Board> boards = boardRepository.findAll(pageable);
//        List<BoardPagingDto> list = new ArrayList<>();
//
//        for (Board board : boards) {
//            BoardPagingDto dto = new BoardPagingDto();
//            dto.setBoardId(board.getId());
//            dto.setTitle(board.getTitle());
//            dto.setCareerImage(board.getCareerImage());
//            List<String> hashtagNames = new ArrayList<>();
//
//            for (BoardHashtag hashtag : board.getHashtag()) {
//                hashtagNames.add(hashtag.getHashtagName());
//            }
//            dto.setHashtags(hashtagNames);
//
//            list.add(dto);
//        }
//
//        return list;
//    }

}
