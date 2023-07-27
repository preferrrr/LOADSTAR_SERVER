package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.request.CreateBoardDto;
import com.lodestar.lodestar_server.dto.request.ModifyBoardDto;
import com.lodestar.lodestar_server.dto.response.BoardPagingDto;
import com.lodestar.lodestar_server.dto.response.CareerDto;
import com.lodestar.lodestar_server.dto.response.CommentDto;
import com.lodestar.lodestar_server.dto.response.GetBoardResponseDto;
import com.lodestar.lodestar_server.entity.*;
import com.lodestar.lodestar_server.exception.AuthFailException;
import com.lodestar.lodestar_server.exception.NotFoundException;
import com.lodestar.lodestar_server.repository.*;
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
    private final HashtagRepository hashtagRepository;
    private final CommentRepository commentRepository;

    public void saveBoard(User user, CreateBoardDto createBoardDto) {

        Board board = new Board();

        board.setUser(user);
        board.setTitle(createBoardDto.getTitle());
        board.setContent(createBoardDto.getContent());

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
            List<String> hashtagNames = new ArrayList<>();

            for (BoardHashtag hashtag : board.getHashtag()) {
                hashtagNames.add(hashtag.getHashtagName());
            }
            dto.setHashtags(hashtagNames);

            List<CareerDto> careerDtos = new ArrayList<>();
            for(Career career : board.getUser().getCareers()) {
                careerDtos.add(career.createDto());
            }
            dto.setArr(careerDtos);

            result.add(dto);
        }

        return result;
    }


    @Transactional(readOnly = true)
    public GetBoardResponseDto getBoard(User user, Long boardId) {

        Board findBoard = boardRepository.findByPathBoardId(boardId).orElseThrow(()->new NotFoundException("[get board] boardId : " + boardId));

        GetBoardResponseDto response = new GetBoardResponseDto();

        response.setBoardId(findBoard.getId());
        response.setTitle(findBoard.getTitle());
        response.setContent(findBoard.getContent());
        response.setCreatedAt(findBoard.getCreatedAt());
        response.setModifiedAt(findBoard.getModifiedAt());


        //TODO: 그래프를 그리기 위한 Career를,
        // 게시글을 작성한 유저를 조회할 때 fetch join으로 같이 가져와서 쿼리 횟수 1번 줄임
        User findUser = userRepository.findByIdWithCareers(findBoard.getUser().getId()).orElseThrow(()->new NotFoundException("[get board] userId : " + findBoard.getUser().getId()));

        response.setUserId(findUser.getId());
        response.setUsername(findUser.getUsername());

        List<Career> careerList = findUser.getCareers();
        List<CareerDto> dtoList = new ArrayList<>();
        for(Career career : careerList) {
            dtoList.add(career.createDto());
        }
        response.setArr(dtoList);



        //현재 로그인한 유저,유저가 이 게시글을 북마크로 동록했는지 안 했는지 체크 (쿼리)
        User bookmarkUser = userRepository.getReferenceById(user.getId());
        boolean bookmark = bookmarkRepository.existsBookmarkByBoardAndUser(findBoard,bookmarkUser);
        response.setBookmark(bookmark);

        List<BoardHashtag> hashtagList = findBoard.getHashtag();
        List<String> hashtags = new ArrayList<>();
        for (BoardHashtag hashtag : hashtagList) {
            hashtags.add(hashtag.getHashtagName());
        }
        response.setHashtags(hashtags);

        //TODO: 댓글 쓴 유저 같이 조회 => commentRepository에서 jpql로 N+1 해결
        //쿼리 5번.
        List<Comment> commentList = commentRepository.findByBoardIdWithUserInfo(findBoard.getId());
        List<CommentDto> comments = new ArrayList<>();

        for(Comment comment : commentList) {
            comments.add(comment.createDto());
        }
        response.setComments(comments);


        return response;
    }


    public void deleteBoard(User user, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("[delete board] boardId : " + boardId));

        if(board.getUser().getId() != user.getId()) {
            throw new AuthFailException(board.getUser().getId() + " != " + user.getId());
        }

        boardRepository.deleteById(boardId);

    }

    public void modifyBoard(User user, Long boardId, ModifyBoardDto modifyBoardDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("[modify board] boardId : " + boardId));

        if(board.getUser().getId() != user.getId()) {
            throw new AuthFailException(board.getUser().getId() + " != " + user.getId());
        }

        board.setTitle(modifyBoardDto.getTitle());
        board.setContent(modifyBoardDto.getContent());


        List<BoardHashtag> savedHashtags = board.getHashtag();
        List<String> savedHashtagNames = new ArrayList<>();
        for(int i = 0 ; i < savedHashtags.size(); i++) {
            savedHashtagNames.add(savedHashtags.get(i).getHashtagName());
        }

        List<String> requestHashtagNames = modifyBoardDto.getHashtags();

        List<BoardHashtag> addHashtags = new ArrayList<>();
        for(int i = 0 ; i < requestHashtagNames.size(); i++) {
            if(!savedHashtagNames.contains(requestHashtagNames.get(i))) {
                BoardHashtag hashtag = new BoardHashtag();
                hashtag.setBoard(board);
                hashtag.setHashtagName(requestHashtagNames.get(i));

                addHashtags.add(hashtag);
            }
        }

        List<BoardHashtag> deleteHashtags = new ArrayList<>();
        for(BoardHashtag hashtag : savedHashtags) {
            if(!requestHashtagNames.contains(hashtag.getHashtagName()))
                deleteHashtags.add(hashtag);
        }


        hashtagRepository.saveAll(addHashtags);
        hashtagRepository.deleteAllInBatch(deleteHashtags);

    }



}
