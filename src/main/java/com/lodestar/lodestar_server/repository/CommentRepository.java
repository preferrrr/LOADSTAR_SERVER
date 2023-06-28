package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
