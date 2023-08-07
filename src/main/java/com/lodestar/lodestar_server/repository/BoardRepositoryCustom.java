package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Board;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {

    List<Board> getBoardList(Pageable pageable, String[] hashtags);
}

