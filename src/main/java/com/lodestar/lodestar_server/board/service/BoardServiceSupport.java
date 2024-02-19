package com.lodestar.lodestar_server.board.service;

import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.board.exception.BoardNotFoundException;
import com.lodestar.lodestar_server.board.exception.UnauthorizedDeleteException;
import com.lodestar.lodestar_server.board.exception.UnauthorizedModifyException;
import com.lodestar.lodestar_server.board.repository.BoardRepository;
import com.lodestar.lodestar_server.bookmark.service.BookmarkServiceSupport;
import com.lodestar.lodestar_server.comment.entity.Comment;
import com.lodestar.lodestar_server.comment.service.CommentServiceSupport;
import com.lodestar.lodestar_server.hashtag.entity.BoardHashtag;
import com.lodestar.lodestar_server.hashtag.repository.HashtagRepository;
import com.lodestar.lodestar_server.hashtag.repository.HashtagRepositoryJdbc;
import com.lodestar.lodestar_server.user.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceSupport {

    private final BoardRepository boardRepository;
    private final HashtagRepository hashtagRepository;
    private final HashtagRepositoryJdbc hashtagRepositoryJdbc;
    private final BookmarkServiceSupport bookmarkServiceSupport;
    private final CommentServiceSupport commentServiceSupport;

    public void saveBoard(Board board) {
        boardRepository.save(board);
    }

    /**
     * 해시태그의 개수만큼 insert 쿼리가 생성되는데, 이는 bulk query 로 해결 가능.
     * 그런데 Id의 전략을 identity를 사용했기 때문에 jpa에서는 bulk query(jpa batch) 사용하지 못한다.
     * => jdbc template 사용해서 해결한다.
     * hashtag 테이블은 identity 전략을 사용하지 않기 때문에 bulk query 가능하다.
     * 결론은 jdbc template 사용해서 bulk query로 insert 성능 개선한다.
     */
    public void saveHashtags(Long id, List<String> hashtags) {
        hashtagRepositoryJdbc.saveHashtags(id, hashtags);
    }

    public List<Board> getBoardList(Pageable pageable, String[] hashtags) {
        if (hashtags.length == 0)
            return boardRepository.getBoardList(pageable);

        return boardRepository.getBoardListWithHashtags(pageable, hashtags);
    }

    public Board getBoardWithHashtagsAndCommentsById(Long boardId) {
        return boardRepository.getBoardWithHashtagsAndCommentsById(boardId).orElseThrow(BoardNotFoundException::new);
    }

    @Transactional(readOnly = false)
    public void increaseViewIfNotViewedBefore(Board board, User user, HttpSession httpSession) {
        List<Long> list = (List<Long>) httpSession.getAttribute("boards");

        if ((!list.contains(board.getId())) && (!board.getUser().getId().equals(user.getId()))) {
            //이미 조회한 게시글이 아니고 작성자도 아니어야해.
            board.addView();
            list.add(board.getId());
            httpSession.setAttribute("boards", list);
        }
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
    }

    public void checkIsBoardWriterForDelete(Board board, User user) {
        if (!board.getUser().getId().equals(user.getId()))
            throw new UnauthorizedDeleteException();
    }

    public void deleteBoardById(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    public Board getBoardWithHashtagsById(Long boardId) {
        return boardRepository.getBoardWithHashtagsById(boardId).orElseThrow(BoardNotFoundException::new);
    }

    public void checkIsBoardWriterForModify(Board board, User user) {
        if (!board.getUser().getId().equals(user.getId()))
            throw new UnauthorizedModifyException();
    }

    public List<String> getAddHashtags(List<BoardHashtag> hashtags, List<String> hashtags1) {

        // 게시글에 저장되어있던 해시태그들.
        Set<String> savedHashtagNames = hashtags.stream()
                .map(boardHashtag -> boardHashtag.getId().getHashtagName())
                .collect(Collectors.toSet());

        return hashtags1.stream()
                .filter(hashtag -> !savedHashtagNames.contains(hashtag))
                .collect(Collectors.toList());
    }

    public List<BoardHashtag> getDeleteHashtags(List<BoardHashtag> hashtags, List<String> hashtags1) {
        Set<String> set = hashtags1.stream().collect(Collectors.toSet());

        return hashtags.stream()
                .filter(hashtag -> !set.contains(hashtag.getId().getHashtagName()))
                .collect(Collectors.toList());
    }

    public void deleteHashtags(List<BoardHashtag> deleteHashtags) {
        hashtagRepository.deleteAllInBatch(deleteHashtags);
    }

    public List<Long> searchBoardByKeyword(Pageable pageable, String keywords) {
        return boardRepository.searchBoards(pageable, modifyKeywordToSearch(keywords)).stream().toList();
    }

    public List<Board> findBoardsWhereInBoardIds(List<Long> boardIds) {
        return boardRepository.findBoardsWhereInBoardIds(boardIds);
    }

    private String modifyKeywordToSearch(String keyword) {
        return keyword.replaceAll(" ", "|");
    }

    public List<Board> getMyBoardList(User user, Pageable pageable) {
        return boardRepository.getMyBoardList(user, pageable);
    }

    public List<Board> getMyBookmarkBoardList(User user, Pageable pageable) {
        return boardRepository.getMyBookmarkBoardList(user, pageable);
    }

    public List<Board> getMyCommentBoardList(User user, Pageable pageable) {
        return boardRepository.getMyCommentBoardList(user, pageable);
    }

    public boolean checkExistsBookmarkByBoardAndUser(Board board, User user) {
        return bookmarkServiceSupport.checkExistsBookmarkByBoardAndUser(board,user);
    }

    public List<Comment> getCommentsWithUserInfoByBoardId(Long id) {
        return commentServiceSupport.getCommentsWithUserInfoByBoardId(id);
    }
}
