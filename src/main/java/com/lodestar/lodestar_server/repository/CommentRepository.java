package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
            "left join fetch c.user " +
            "where c.board.id = :boardId")
    List<Comment> findByBoardIdWithUserInfo(@Param("boardId")Long boardId);

}
