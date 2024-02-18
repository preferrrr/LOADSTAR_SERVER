package com.lodestar.lodestar_server.bookmark.repository;

import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.bookmark.entity.Bookmark;
import com.lodestar.lodestar_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query(value = "select m.board_id from bookmark m " +
            "where m.user_id = :userId",
            nativeQuery = true)
    List<Long> findBoardIdByUserId(@Param("userId") Long userId);


    boolean existsBookmarkByBoardAndUser(Board board, User user);


    void deleteByBoardAndUser(Board board, User user);


}
