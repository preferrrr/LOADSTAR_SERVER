package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.*;
import com.lodestar.lodestar_server.entity.*;
import com.lodestar.lodestar_server.exception.AuthFailException;
import com.lodestar.lodestar_server.repository.BoardRepository;
import com.lodestar.lodestar_server.repository.BookmarkRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final CareerService careerService;

    public void saveBoard(User user, CreateBoardDto createBoardDto) {

        Board board = new Board();

        board.setUser(user);
        board.setTitle(createBoardDto.getTitle());
        board.setContent(createBoardDto.getContent());
        //TODO: 커리어 이미지 y이면 이미지 저장, 아니면 n으로
        board.setCareerImage(createBoardDto.getShowGraph());

        List<BoardHashtag> hashtags = new ArrayList<>();
        List<String> hashtagNames = createBoardDto.getHashtags();

        //TODO: 해시태그의 개수만큼 insert 쿼리가 생성됨 => bulk query로 해결 가능
        // 근데 Id의 전략을 identity를 사용했기 때문에 jpa에서는 bulk query 사용 불가 => jdbc template 사용해서 해결 가능.
        for (int i = 0; i < hashtagNames.size(); i++) {
            BoardHashtag hashtag = new BoardHashtag();
            hashtag.setBoard(board);
            hashtag.setHashtagName(hashtagNames.get(i));
            hashtags.add(hashtag);
        }


        String current = user.getCurrent();
        if(current.equals("y") || current.equals("n")) {
            BoardHashtag hashtag = new BoardHashtag();
            hashtag.setBoard(board);
            if (current.equals("y"))
                hashtag.setHashtagName("현직자");
            else
                hashtag.setHashtagName("비현직자");

            hashtags.add(hashtag);
        }


        String major = user.getMajor();
        if(major.equals("y") || major.equals("n")) {
            BoardHashtag hashtag = new BoardHashtag();
            hashtag.setBoard(board);
            if (major.equals("y"))
                hashtag.setHashtagName("전공자");
            else
                hashtag.setHashtagName("비전공자");

            hashtags.add(hashtag);
        }

        String frontBack = user.getFront_back();
        if(frontBack.equals("y") || frontBack.equals("n")) {
            BoardHashtag hashtag = new BoardHashtag();
            hashtag.setBoard(board);
            if (frontBack.equals("y"))
                hashtag.setHashtagName("front");
            else
                hashtag.setHashtagName("back");

            hashtags.add(hashtag);
        }

        board.setHashtag(hashtags);
        boardRepository.save(board);

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
    public GetBoardResponseDto getBoard(User user, Long boardId) {

        Board findBoard = boardRepository.findByPathBoardId(boardId);

        GetBoardResponseDto response = new GetBoardResponseDto();

        response.setBoardId(findBoard.getId());
        response.setTitle(findBoard.getTitle());
        response.setContent(findBoard.getContent());
        response.setCreatedAt(findBoard.getCreatedAt());
        response.setModifiedAt(findBoard.getModifiedAt());


        //게시글을 작성한 유저
        User findUser = userRepository.findById(findBoard.getUser().getId()).orElseThrow(() -> new AuthFailException(String.valueOf(boardId)));
        response.setUserId(findUser.getId());
        response.setUsername(findUser.getUsername());


        List<CareerDto> careerList = careerService.getCareer(findUser);
        response.setArr(careerList);

        response.setCareerImage(findBoard.getCareerImage());

        User bookmarkUser = userRepository.getReferenceById(user.getId());
        boolean bookmark = bookmarkRepository.existsBookmarkByBoardAndUser(findBoard,bookmarkUser);
        response.setBookmark(bookmark);

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
            commentDto.setUsername(comment.getUser().getUsername());
            commentDto.setCommentContent(comment.getContent());
            commentDto.setUserId(comment.getUser().getId());
            commentDto.setCreatedAt(comment.getCreatedAt());
            commentDto.setModifiedAt(comment.getModifiedAt());

            comments.add(commentDto);
        }

        response.setComments(comments);

        return response;
    }


    public void deleteBoard(User user, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new AuthFailException(String.valueOf(boardId)));

        if(board.getUser().getId() != user.getId()) {
            throw new AuthFailException(board.getUser().getId() + " != " + user.getId());
        }

        boardRepository.deleteById(boardId);

    }



    private final ImageService imageService;
    public String imageTest(TestDto testDto, MultipartFile file) throws Exception {

        imageService.saveImage(file);

        return testDto.getData();
    }


//    public void saveBoard100(CreateBoardDto createBoardDto) {
//        User user = userRepository.findById(createBoardDto.getUserId()).orElseThrow(() -> new AuthFailException(String.valueOf(createBoardDto.getUserId())));
//
//        for (int j = 0; j < 100; j++) {
//            Board board = new Board();
//
//            board.setUser(user);
//            board.setTitle("test " + j);
//            board.setContent(createBoardDto.getContent());
//            //TODO: 커리어 이미지 y이면 이미지 저장, 아니면 n으로
//            board.setCareerImage(createBoardDto.getShowGraph());
//
//            List<BoardHashtag> hashtags = new ArrayList<>();
//            List<String> hashtagNames = createBoardDto.getHashtags();
//
//            for (int i = 0; i < hashtagNames.size(); i++) {
//                BoardHashtag hashtag = new BoardHashtag();
//                hashtag.setBoard(board);
//                hashtag.setHashtagName(hashtagNames.get(i));
//                hashtags.add(hashtag);
//            }
//
//            boardRepository.save(board);
//            hashtagRepository.saveAll(hashtags);
//        }
//    }

}
