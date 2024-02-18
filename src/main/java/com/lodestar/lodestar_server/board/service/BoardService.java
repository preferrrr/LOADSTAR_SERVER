package com.lodestar.lodestar_server.board.service;

import com.lodestar.lodestar_server.board.dto.response.GetBoardListDto;
import com.lodestar.lodestar_server.board.dto.response.GetBoardResponseDto;
import com.lodestar.lodestar_server.board.dto.response.MyBoardDto;
import com.lodestar.lodestar_server.board.dto.response.MyBookmarkBoardDto;
import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.bookmark.service.BookmarkServiceSupport;
import com.lodestar.lodestar_server.career.dto.response.CareerDto;
import com.lodestar.lodestar_server.career.service.CareerServiceSupport;
import com.lodestar.lodestar_server.comment.dto.response.CommentDto;
import com.lodestar.lodestar_server.comment.entity.Comment;
import com.lodestar.lodestar_server.board.dto.request.CreateBoardDto;
import com.lodestar.lodestar_server.board.dto.request.ModifyBoardDto;
import com.lodestar.lodestar_server.comment.service.CommentServiceSupport;
import com.lodestar.lodestar_server.exception.AuthFailException;
import com.lodestar.lodestar_server.exception.NotFoundException;
import com.lodestar.lodestar_server.hashtag.entity.BoardHashtag;
import com.lodestar.lodestar_server.user.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardServiceSupport boardServiceSupport;
    private final CareerServiceSupport careerServiceSupport;
    private final BookmarkServiceSupport bookmarkServiceSupport;
    private final CommentServiceSupport commentServiceSupport;

    @Transactional(readOnly = false)
    public void saveBoard(User user, CreateBoardDto createBoardDto) {

        //Board 엔티티 생성
        Board board = Board.create(user, createBoardDto.getTitle(), createBoardDto.getContent());

        //Board 저장
        boardServiceSupport.saveBoard(board);

        //Board의 hashtag 저장
        boardServiceSupport.saveHashtags(board.getId(), createBoardDto.getHashtags());

    }


    @Transactional(readOnly = true)
    public List<GetBoardListDto> getBoardList(Pageable pageable, String[] hashtags) {

        List<Board> boards = boardServiceSupport.getBoardList(pageable, hashtags);

        return boardServiceSupport.createGetBoardListDtos(boards);
    }


    @Transactional(readOnly = false)
    public GetBoardResponseDto getBoard(HttpSession httpSession, User user, Long boardId) {

        //조회할 게시글
        Board board = boardServiceSupport.getBoardWithHashtagsAndCommentsById(boardId);

        //조회수 증가
        boardServiceSupport.increaseViewIfNotViewedBefore(board, user, httpSession);

        //작성자의 커리어
        List<CareerDto> careerDtos = careerServiceSupport.getCareerDtoList(user);

        //내가 북마크 해뒀는지 여부
        boolean isBookmarked = bookmarkServiceSupport.checkExistsBookmarkByBoardAndUser(board, user);

        //게시글 해시태그
        List<String> hashtagNames = board.getHashtags().stream()
                .map(hashtag -> hashtag.getId().getHashtagName())
                .collect(Collectors.toList());

        //댓글
        List<Comment> commentList = commentServiceSupport.getCommentsWithUserInfoByBoardId(board.getId());

        List<CommentDto> comments = commentList.stream()
                .map(comment -> CommentDto.builder()
                        .commentId(comment.getId())
                        .commentContent(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .userId(comment.getUser().getId())
                        .username(comment.getUser().getUsername())
                        .build())
                .collect(Collectors.toList());


        return GetBoardResponseDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .view(board.getView())
                .bookmarkCount(board.getBookmarkCount())
                .userId(user.getId())
                .username(user.getUsername())
                .arr(careerDtos)
                .bookmark(isBookmarked)
                .hashtags(hashtagNames)
                .comments(comments)
                .build();
    }


    public void deleteBoard(User user, Long boardId) {

        //삭제할 게시글
        Board board = boardServiceSupport.getBoardById(boardId);

        //게시글 작성자가 맞는지 체크
        boardServiceSupport.checkIsBoardWriterForDelete(board, user);

        //게시글 삭제
        boardServiceSupport.deleteBoardById(boardId);
    }

    public void modifyBoard(User user, Long boardId, ModifyBoardDto modifyBoardDto) {

        //수정할 게시글
        Board board = boardServiceSupport.getBoardWithHashtagsById(boardId);

        //게시글 작성자가 맞는지 체크
        boardServiceSupport.checkIsBoardWriterForModify(board, user);

        //제목, 내용 수정
        board.modifyBoard(modifyBoardDto.getTitle(), modifyBoardDto.getContent());

        //추가할 해시태그
        List<String> addHashtags = boardServiceSupport.getAddHashtags(board.getHashtags(), modifyBoardDto.getHashtags());

        List<BoardHashtag> deleteHashtags = boardServiceSupport.getDeleteHashtags(board.getHashtags(), modifyBoardDto.getHashtags());
        //삭제할 해시태그들


        boardServiceSupport.deleteHashtags(deleteHashtags);
        boardServiceSupport.saveHashtags(boardId, addHashtags);
    }


    @Transactional(readOnly = true)
    public List<GetBoardListDto> searchBoards(Pageable pageable, String keywords) {

        //키워드로 검색한 게시글들의 id
        List<Long> boardIds = boardServiceSupport.searchBoardByKeyword(pageable, keywords);

        //id로 게시글 검색
        List<Board> boards = boardServiceSupport.findBoardsWhereInBoardIds(boardIds);

        return boardServiceSupport.createGetBoardListDtos(boards);
    }

    @Transactional(readOnly = true)
    public List<MyBoardDto> getMyBoardList(User user, Pageable pageable) {

        List<Board> myBoards = boardServiceSupport.getMyBoardList(user, pageable);

        return boardServiceSupport.entityToMyBoardDtos(myBoards);
    }


    @Transactional(readOnly = true)
    public List<MyBookmarkBoardDto> getMyBookmarkBoardList(User user, Pageable pageable) {

        List<Board> boards = boardServiceSupport.getMyBookmarkBoardList(user, pageable);

        return boardServiceSupport.entityToMyBookmarkBoardDtos(boards);
    }


    @Transactional(readOnly = true)
    public List<GetBoardListDto> getMyCommentBoardList(User user, Pageable pageable) {

        List<Board> boards = boardServiceSupport.getMyCommentBoardList(user, pageable);

        return boardServiceSupport.createGetBoardListDtos(boards);
    }


}
