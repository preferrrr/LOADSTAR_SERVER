package com.lodestar.lodestar_server.comment.repository;

import com.lodestar.lodestar_server.comment.entity.Comment;
import com.lodestar.lodestar_server.user.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findCommentsWithUserInfoByBoardId(Long boardId);

    List<Comment> getMyComments(User user, Pageable pageable);
}
