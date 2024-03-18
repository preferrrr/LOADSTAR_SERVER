package com.lodestar.lodestar_server.board.service;

import com.lodestar.lodestar_server.board.dto.response.GetBoardListResponseDto;
import com.lodestar.lodestar_server.board.dto.response.GetBoardResponseDto;
import com.lodestar.lodestar_server.board.dto.response.GetMyBoardListResponseDto;
import com.lodestar.lodestar_server.board.dto.response.MyBookmarkBoardListResponseDto;
import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.comment.entity.Comment;
import com.lodestar.lodestar_server.board.dto.request.CreateBoardDto;
import com.lodestar.lodestar_server.board.dto.request.ModifyBoardDto;
import com.lodestar.lodestar_server.hashtag.entity.BoardHashtag;
import com.lodestar.lodestar_server.user.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardServiceSupport boardServiceSupport;

    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "boardList", allEntries = true),
            @CacheEvict(value = "keyword", allEntries = true)
    })
    public void saveBoard(User user, CreateBoardDto createBoardDto) {

        //Board 엔티티 생성
        Board board = Board.create(user, createBoardDto.getTitle(), createBoardDto.getContent());

        //Board 저장
        boardServiceSupport.saveBoard(board);

        //Board의 hashtag 저장
        boardServiceSupport.saveHashtags(board.getId(), createBoardDto.getHashtags());

    }


    @Transactional(readOnly = true)
    @Cacheable(value = "boardList", key = "#hashtags", condition = "#hashtags.length > 0")
    public GetBoardListResponseDto getBoardList(Pageable pageable, String[] hashtags) {

        List<Board> boards = boardServiceSupport.getBoardList(pageable, hashtags);

        return GetBoardListResponseDto.of(boards);
    }


    @Transactional(readOnly = false)
    @Cacheable(value = "board", key = "#boardId", condition = "#boardId != null", cacheManager = "cacheManager")
    public GetBoardResponseDto getBoard(HttpSession httpSession, User user, Long boardId) {

        //조회할 게시글
        Board board = boardServiceSupport.getBoardWithHashtagsAndCommentsById(boardId);

        //조회수 증가
        boardServiceSupport.increaseViewIfNotViewedBefore(board, user, httpSession);

        //댓글
        List<Comment> comments = boardServiceSupport.getCommentsWithUserInfoByBoardId(board.getId());

        return GetBoardResponseDto.of(board, comments);
    }


    @CacheEvict(value = "board", key = "#boardId")
    public void deleteBoard(User user, Long boardId) {

        //삭제할 게시글
        Board board = boardServiceSupport.getBoardById(boardId);

        //게시글 작성자가 맞는지 체크
        boardServiceSupport.checkIsBoardWriterForDelete(board, user);

        //게시글 삭제
        boardServiceSupport.deleteBoardById(boardId);
    }

    @CacheEvict(value = "board", key = "#boardId")
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
    @Cacheable(value = "keyword", key = "#keywords", condition = "#keywords.length() != 0")
    public GetBoardListResponseDto searchBoards(Pageable pageable, String keywords) {

        //키워드로 검색한 게시글들의 id
        List<Long> boardIds = boardServiceSupport.searchBoardByKeyword(pageable, keywords);

        //id로 게시글 검색
        List<Board> boards = boardServiceSupport.findBoardsWhereInBoardIds(boardIds);

        return GetBoardListResponseDto.of(boards);
    }


    @Transactional(readOnly = true)
    public GetMyBoardListResponseDto getMyBoardList(User user, Pageable pageable) {

        List<Board> boards = boardServiceSupport.getMyBoardList(user, pageable);

        return GetMyBoardListResponseDto.of(boards);
    }


    @Transactional(readOnly = true)
    public MyBookmarkBoardListResponseDto getMyBookmarkBoardList(User user, Pageable pageable) {

        List<Board> boards = boardServiceSupport.getMyBookmarkBoardList(user, pageable);

        return MyBookmarkBoardListResponseDto.of(boards);
    }


    @Transactional(readOnly = true)
    public GetBoardListResponseDto getMyCommentBoardList(User user, Pageable pageable) {

        List<Board> boards = boardServiceSupport.getMyCommentBoardList(user, pageable);

        return GetBoardListResponseDto.of(boards);
    }


}
