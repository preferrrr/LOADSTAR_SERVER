package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.request.CreateBoardDto;
import com.lodestar.lodestar_server.dto.request.ModifyBoardDto;
import com.lodestar.lodestar_server.dto.response.*;
import com.lodestar.lodestar_server.entity.*;
import com.lodestar.lodestar_server.exception.AuthFailException;
import com.lodestar.lodestar_server.exception.NotFoundException;
import com.lodestar.lodestar_server.repository.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final BookmarkRepository bookmarkRepository;
    private final HashtagRepository hashtagRepository;
    private final CommentRepository commentRepository;
    private final HashtagRepositoryJdbc hashtagRepositoryJdbc;
    private final CareerService careerService;

    public void saveBoard(User user, CreateBoardDto createBoardDto) {

        Board board = Board.builder()
                .user(user)
                .title(createBoardDto.getTitle())
                .content(createBoardDto.getContent())
                .build();

//      List<BoardHashtag> hashtags = new ArrayList<>();
        List<String> hashtagNames = createBoardDto.getHashtags();

        //TODO: 해시태그의 개수만큼 insert 쿼리가 생성됨 => bulk query로 해결 가능
        // 근데 Id의 전략을 identity를 사용했기 때문에 jpa에서는 bulk query(jpa batch) 사용 불가 => jdbc template 사용해서 해결 가능.
        // hashtag 테이블은 identity 전략을 사용하지 않음으로써 bulk query 가능
        // => hashtag 테이블 auto increment 기본키 제거, (board_id, hashtag_name) 기본키로 바꿈
        // => jdbc template 사용해서 bulk query로 insert 성능 개선.
//        for (int i = 0; i < hashtagNames.size(); i++) {
//            BoardHashtag hashtag = new BoardHashtag();
//
//            BoardHashtagId id = new BoardHashtagId();
//            id.setHashtagName(hashtagNames.get(i));
//
//            hashtag.setBoardHashtagId(id);
//            hashtag.setBoard(board);
//
//            hashtags.add(hashtag);
//        }
//        board.setHashtag(hashtags);

        boardRepository.save(board); // 트랜잭션이 걸려있기 때문에 둘 다 성공해야 저장됨.
        hashtagRepositoryJdbc.saveHashtags(board.getId(), hashtagNames);

    }


    @Transactional(readOnly = true)
    public List<BoardPagingDto> getBoardList(Pageable pageable, String[] hashtags) {

        List<Board> boards = boardRepository.getBoardList(pageable,hashtags);

        List<BoardPagingDto> result = createDtos(boards);

        return result;
    }


    public GetBoardResponseDto getBoard(HttpSession httpSession, User user, Long boardId) {

        //Board findBoard = boardRepository.findByPathBoardId(boardId).orElseThrow(()->new NotFoundException("[get board] boardId : " + boardId));
        Board findBoard = boardRepository.getBoardWithHashAndComById(boardId).orElseThrow(()->new NotFoundException("[get board] boardId : " + boardId));

        //조회수 처리를 위한 로직
        //session의 boards에 게시글 인덱스가 있으면 조회수 증가 X
        //게시글 작성자라면 조회수 증가 X
        //session의 boards에 게시글 인덱스가 없으면 조회수 증가 O, 게시글 인덱스 추가
        // => boards에 게시글 인덱스가 없고, 조회한 사람이 게시글 작성자가 아니고
        List<Long> list = (List<Long>) httpSession.getAttribute("boards");

        if((!list.contains(boardId)) && (!findBoard.getUser().getId().equals(user.getId()))) {
            //이미 조회한 게시글이 아니고 작성자도 아니어야해.
            findBoard.addView();
            list.add(boardId);
            httpSession.setAttribute("boards",list);
        }

//        User findUser = userRepository.findUserWithCareersById(findBoard.getUser().getId()).orElseThrow(()->new NotFoundException("[get board] userId : " + findBoard.getUser().getId()));
//        List<CareerDto> dtoList = new ArrayList<>();
//        List<Career> careerList = findUser.getCareers();
//        for(Career career : careerList) {
//            dtoList.add(career.createDto());
//        }
        //user와 join해서 조회하던 career를 redis에 user 객체를 저장함으로써 career만 조회하도록 수정.
        List<CareerDto> dtoList = careerService.getCareer(user);


        //현재 로그인한 유저,유저가 이 게시글을 북마크로 동록했는지 안 했는지 체크 (쿼리)
        //위에서 findUserWithCareersById로 조회할 때 내가 북마크한 게시글들도 가져와서, 현재 게시글이 포함되어 있는지
        //검사하는게 더 빠를 수도 있을까?하는 생각....
        //하지만 근본적으로 ToMany는 2개 이상 fetch join 할 수 없음. careers와 bookmarks가 둘 다 user에게 ToMany임.
        //카테시안 곱에 의해 중복 데이터 발생하는데 이때 Hibernate는 올바른 열을 올바른 엔티티에 매핑 할 수 없다.
        boolean bookmark = bookmarkRepository.existsBookmarkByBoardAndUser(findBoard,user);

        List<BoardHashtag> hashtagList = findBoard.getHashtags();
        List<String> hashtagNames = new ArrayList<>();
        for (BoardHashtag hashtag : hashtagList) {
            hashtagNames.add(hashtag.getId().getHashtagName());
        }

        List<Comment> commentList = commentRepository.findCommentsWithUserInfoByBoardId(findBoard.getId());
        List<CommentDto> comments = new ArrayList<>();

        for(Comment comment : commentList) {
            CommentDto dto = CommentDto.builder()
                    .commentId(comment.getId())
                    .commentContent(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .userId(comment.getUser().getId())
                    .username(comment.getUser().getUsername())
                    .build();

            comments.add(dto);
        }

        GetBoardResponseDto response = GetBoardResponseDto.builder()
                .boardId(findBoard.getId())
                .title(findBoard.getTitle())
                .content(findBoard.getContent())
                .createdAt(findBoard.getCreatedAt())
                .modifiedAt(findBoard.getModifiedAt())
                .view(findBoard.getView())
                .bookmarkCount(findBoard.getBookmarkCount())
                .userId(user.getId())
                .username(user.getUsername())
                .arr(dtoList)
                .bookmark(bookmark)
                .hashtags(hashtagNames)
                .comments(comments)
                .build();

        return response;
    }


    public void deleteBoard(User user, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("[delete board] boardId : " + boardId));

        if(!board.getUser().getId().equals(user.getId()))
            throw new AuthFailException(board.getUser().getId() + " != " + user.getId());
        // 처음엔 AuthenticationPrincipal의 User가 필터에서 userId만을 사용하기 위해서 임시로 생성해줬는데,
        // redis에 User 객체를 저장하고 가져오는 걸로 바꾸면서 Long 타입 비교에 equals를 사용해야함.

        boardRepository.deleteById(boardId);

    }

    public void modifyBoard(User user, Long boardId, ModifyBoardDto modifyBoardDto) {
        Board board = boardRepository.getBoardWithHashtagsById(boardId).orElseThrow(() -> new NotFoundException("[modify board] boardId : " + boardId));

        if(!board.getUser().getId().equals(user.getId()) )
            throw new AuthFailException(board.getUser().getId() + " != " + user.getId());

        board.modifyBoard(modifyBoardDto.getTitle(), modifyBoardDto.getContent());

        List<BoardHashtag> savedHashtags = board.getHashtags(); // 게시글에 저장되어있던 해시태그들.
        List<String> savedHashtagNames = new ArrayList<>();
        for(int i = 0 ; i < savedHashtags.size(); i++) {
            savedHashtagNames.add(savedHashtags.get(i).getId().getHashtagName());
        }

        List<String> requestHashtagNames = modifyBoardDto.getHashtags(); // 요청된 해시태그들.

        List<String> addHashtags = new ArrayList<>(); // 추가할 해시태그들.
        //요청된 해시태그가 저장되어있던 해시태그에 포함되어있지 않으면 추가
        for(int i = 0 ; i < requestHashtagNames.size(); i++) {
            if(!savedHashtagNames.contains(requestHashtagNames.get(i))) {
                addHashtags.add(requestHashtagNames.get(i));
            }
        }

        List<BoardHashtag> deleteHashtags = new ArrayList<>(); // 삭제할 해시태그들.
        //저장되어있던 해시태그가 요청된 해시태그에 없으면 삭제.
        for(BoardHashtag hashtag : savedHashtags) {
            if(!requestHashtagNames.contains(hashtag.getId().getHashtagName()))
                deleteHashtags.add(hashtag);
        }

        hashtagRepository.deleteAllInBatch(deleteHashtags);
        hashtagRepositoryJdbc.saveHashtags(boardId, addHashtags);

    }


    @Transactional(readOnly = true)
    public List<BoardPagingDto> searchBoards(Pageable pageable, String keywords) {

        String param = keywords.replaceAll(" ", "|");

        Page<Long> pagingIds = boardRepository.searchBoards(pageable, param);


        List<Long> boardIds = new ArrayList<>();
        for (Long id : pagingIds) {
            boardIds.add(id);
        }

        List<Board> boards= boardRepository.findBoardsWhereInBoardIds(boardIds);

        List<BoardPagingDto> result = createDtos(boards);

        return result;
    }

    @Transactional(readOnly = true)
    public List<MyBoardDto> getMyBoardList(User user, Pageable pageable) {

        List<Board> boards = boardRepository.getMyBoardList(user, pageable);

        List<MyBoardDto> result = new ArrayList<>();

        for(Board board : boards) {
            MyBoardDto dto = MyBoardDto.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .bookmarkCount(board.getBookmarkCount())
                    .view(board.getView())
                    .createdAt(board.getCreatedAt())
                    .modifiedAt(board.getModifiedAt())
                    .build();

            result.add(dto);
        }

        return result;
    }


    @Transactional(readOnly = true)
    public List<MyBookmarkBoardDto> getMyBookmarkBoardList(User user, Pageable pageable) {

        List<Board> boards = boardRepository.getMyBookmarkBoardList(user, pageable);

        List<MyBookmarkBoardDto> result = new ArrayList<>();

        for (Board board : boards) {

            MyBookmarkBoardDto dto = MyBookmarkBoardDto.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .username(board.getUser().getUsername())
                    .view(board.getView())
                    .bookmarkCount(board.getBookmarkCount())
                    .createdAt(board.getCreatedAt())
                    .modifiedAt(board.getModifiedAt())
                    .build();

            result.add(dto);
        }



        return result;
    }


    @Transactional(readOnly = true)
    public List<BoardPagingDto> getMyCommentBoardList(User user, Pageable pageable) {

        List<Board> boards = boardRepository.getMyCommentBoardList(user, pageable);

        List<BoardPagingDto> result = createDtos(boards);

        return result;
    }

    private List<BoardPagingDto> createDtos(List<Board> boards) {
        List<BoardPagingDto> result = new ArrayList<>();

        for (Board board : boards) {

            List<String> hashtagNames = new ArrayList<>();

            for (BoardHashtag hashtag : board.getHashtags()) {
                hashtagNames.add(hashtag.getId().getHashtagName());
            }

            List<CareerDto> careerDtos = new ArrayList<>();
            for(Career career : board.getUser().getCareers()) {
                careerDtos.add(career.createDto());
            }

            BoardPagingDto dto = BoardPagingDto.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .view(board.getView())
                    .bookmarkCount(board.getBookmarkCount())
                    .hashtags(hashtagNames)
                    .arr(careerDtos)
                    .username(board.getUser().getUsername())
                    .createdAt(board.getCreatedAt())
                    .modifiedAt(board.getModifiedAt())
                    .build();

            result.add(dto);
        }

        return result;

    }

}
