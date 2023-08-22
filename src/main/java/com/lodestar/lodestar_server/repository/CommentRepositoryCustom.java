package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Comment;
import com.lodestar.lodestar_server.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findCommentsWithUserInfoByBoardId(Long boardId);

    List<Comment> getMyComments(User user, Pageable pageable);
}
