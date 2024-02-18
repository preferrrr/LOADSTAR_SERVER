package com.lodestar.lodestar_server.board.repository;

import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.user.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BoardRepositoryCustom {

    List<Board> getBoardList(Pageable pageable, String[] hashtags);

    Optional<Board> getBoardWithHashAndComById(Long boardId);

    Optional<Board> getBoardWithHashtagsById(Long boardId);

    List<Board> getMyBoardList(User user , Pageable pageable);

    List<Board> getMyBookmarkBoardList(User user, Pageable pageable);

    List<Board> getMyCommentBoardList(User user, Pageable pageable);
}

