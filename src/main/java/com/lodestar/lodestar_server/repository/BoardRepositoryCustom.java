package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Board;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BoardRepositoryCustom {

    List<Board> getBoardList(Pageable pageable, String[] hashtags);

    Optional<Board> getBoardWithHashAndComById(Long boardId);

    Optional<Board> getBoardWithHashtagsById(Long boardId);
}

