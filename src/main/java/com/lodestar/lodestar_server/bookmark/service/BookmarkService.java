package com.lodestar.lodestar_server.bookmark.service;

import com.lodestar.lodestar_server.board.service.BoardServiceSupport;
import com.lodestar.lodestar_server.bookmark.dto.request.SaveBookmarkDto;
import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.bookmark.entity.Bookmark;
import com.lodestar.lodestar_server.common.util.CurrentUserGetter;
import com.lodestar.lodestar_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkServiceSupport bookmarkServiceSupport;
    private final BoardServiceSupport boardServiceSupport;
    private final CurrentUserGetter currentUserGetter;

    @Transactional(readOnly = false)
    public void saveBookmark(SaveBookmarkDto saveBookmarkDto) {

        User user = currentUserGetter.getCurrentUser();

        Board board = boardServiceSupport.getBoardById(saveBookmarkDto.getBoardId());

        bookmarkServiceSupport.checkExistsBookmarkForSaveBookmark(board, user);

        Bookmark bookmark = Bookmark.create(board, user);

        bookmarkServiceSupport.saveBookmark(bookmark);

        board.addBookmarkCount();
    }

    @Transactional(readOnly = false)
    public void deleteBookmark(Long boardId) {

        User user = currentUserGetter.getCurrentUser();

        Board board = boardServiceSupport.getBoardById(boardId);

        bookmarkServiceSupport.checkExistsBookmarkForDeleteBookmark(board, user);

        bookmarkServiceSupport.deleteBookmark(board, user);

        board.subBookmarkCount();
    }


}
