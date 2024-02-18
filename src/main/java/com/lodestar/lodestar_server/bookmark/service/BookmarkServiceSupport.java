package com.lodestar.lodestar_server.bookmark.service;

import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.bookmark.repository.BookmarkRepository;
import com.lodestar.lodestar_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkServiceSupport {

    private final BookmarkRepository bookmarkRepository;

    public boolean checkExistsBookmarkByBoardAndUser(Board findBoard, User user) {
        return bookmarkRepository.existsBookmarkByBoardAndUser(findBoard, user);
    }
}
