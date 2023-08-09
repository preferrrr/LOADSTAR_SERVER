package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Comment;
import com.lodestar.lodestar_server.entity.QComment;
import com.lodestar.lodestar_server.entity.QUser;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    private static QComment comment = QComment.comment;
    private static QUser user = QUser.user;


    @Override
    public List<Comment> findCommentsWithUserInfoByBoardId(Long boardId) {

        JPAQuery jpaQuery = jpaQueryFactory
                .selectFrom(comment)
                .join(comment.user, user).fetchJoin()
                .where(comment.board.id.eq(boardId));

        List<Comment> result = jpaQuery.fetch();

        return result;
    }
}
