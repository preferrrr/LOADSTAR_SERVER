package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long id);

    List<Board> findByUserId(Long userId);

    @Query("select b from Board b " +
            "left join fetch b.hashtag")
    Page<Board> findAll(Pageable pageable);


    @Query("select b from Board b " +
            "join fetch b.hashtag h " +
            "where b.id in :boardIds " +
            "order by b.createdAt desc")
    List<Board> findBoardsWhereInBoardIds(@Param("boardIds") List<Long> boardIds);


    @Query(nativeQuery = true,
            value = "select b.board_id from board b join board_hashtag h on b.board_id = h.board_id " +
                    "where hashtag_name in (:hashtags) " +
                    "group by b.board_id",
            countQuery = "select count(b.board_id) from board b join board_hashtag h on b.board_id = h.board_id")
    Page<Long> findBoardIdByHashtags(Pageable pageable, @Param("hashtags") List<String> hashtags);

}
