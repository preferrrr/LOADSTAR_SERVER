package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.request.SaveBookmarkDto;
import com.lodestar.lodestar_server.entity.Board;
import com.lodestar.lodestar_server.entity.Bookmark;
import com.lodestar.lodestar_server.entity.BookmarkId;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.exception.DuplicateBookmarkException;
import com.lodestar.lodestar_server.exception.NotFoundException;
import com.lodestar.lodestar_server.repository.BoardRepository;
import com.lodestar.lodestar_server.repository.BookmarkRepository;
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

    public void saveBookmark(User user, SaveBookmarkDto saveBookmarkDto) {

        Board board = boardRepository.getReferenceById(saveBookmarkDto.getBoardId());

        if (bookmarkRepository.existsBookmarkByBoardAndUser(board, user))
            throw new DuplicateBookmarkException("userId: " + user.getId() + ", boardId: " + board.getId());

        Bookmark bookmark = Bookmark.builder()
                .board(board)
                .user(user)
                .build();

        bookmarkRepository.save(bookmark);

        board = boardRepository.findById(saveBookmarkDto.getBoardId()).orElseThrow(() -> new NotFoundException("[get board] boardId : " + saveBookmarkDto.getBoardId()));
        board.addBookmarkCount();



    }

    public void deleteBookmark(User user, Long boardId) {
        Board board = boardRepository.getReferenceById(boardId);

        if (!bookmarkRepository.existsBookmarkByBoardAndUser(board, user))
            throw new NotFoundException("userId: " + user.getId() + ", boardId: " + board.getId());
        //북마크 테이블에 등록된 북마크가 있어야 하는데, 없다면 어떤 에러를 반환해주지 ?,,,, 찾을 수 없는거니까 NOT FOUND
        //아니면 BAD REQUEST?

        board = boardRepository.findById(boardId).orElseThrow(()-> new NotFoundException("[get board] boardId : " + boardId));

        board.subBookmarkCount();

        bookmarkRepository.deleteByBoardAndUser(board, user);
    }


}
