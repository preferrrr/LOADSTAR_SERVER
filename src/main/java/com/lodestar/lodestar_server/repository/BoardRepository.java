package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long id);

    @Query("select b from Board b")
    List<Board> findAllBoard();

    List<Board> findByUserId(Long userId);

    @Query("select b from Board b " +
            "left join fetch b.hashtag " +
            "order by b.createdAt desc")
    Page<Board> findAll(Pageable pageable);
}
