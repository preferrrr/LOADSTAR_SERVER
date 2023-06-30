package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.SaveBookmarkDto;
import com.lodestar.lodestar_server.entity.Board;
import com.lodestar.lodestar_server.entity.Bookmark;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.repository.BoardRepository;
import com.lodestar.lodestar_server.repository.BookmarkRepository;
import com.lodestar.lodestar_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public void saveBookmark(User user, SaveBookmarkDto saveBookmarkDto) {
        Bookmark bookmark = new Bookmark();

        Board board = boardRepository.getReferenceById(saveBookmarkDto.getBoardId());
        bookmark.setBoard(board);
        bookmark.setUser(user);

        bookmarkRepository.save(bookmark);
    }

    public void deleteBookmark(User user, Long boardId) {
        Board board = boardRepository.getReferenceById(boardId);

        bookmarkRepository.deleteByBoardAndUser(board, user);
    }


}
