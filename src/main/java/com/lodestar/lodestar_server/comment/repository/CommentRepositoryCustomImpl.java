package com.lodestar.lodestar_server.comment.repository;

import com.lodestar.lodestar_server.board.entity.QBoard;
import com.lodestar.lodestar_server.comment.entity.Comment;
import com.lodestar.lodestar_server.comment.entity.QComment;
import com.lodestar.lodestar_server.user.entity.QUser;
import com.lodestar.lodestar_server.user.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    private static QComment comment = QComment.comment;
    private static QUser user = QUser.user;

    private static QBoard board = QBoard.board;


    @Override
    public List<Comment> findCommentsWithUserInfoByBoardId(Long boardId) {

        JPAQuery<Comment> jpaQuery = jpaQueryFactory
                .selectFrom(comment)
                .join(comment.user, user).fetchJoin()
                .where(comment.board.id.eq(boardId));

        List<Comment> result = jpaQuery.fetch();

        return result;
    }

    @Override
    public List<Comment> getMyComments(User user, Pageable pageable) {

        JPAQuery<Comment> jpaQuery = jpaQueryFactory
                .select(comment)
                .distinct()
                .from(comment)
                .join(comment.board, board).fetchJoin()
                .where(comment.user.id.eq(user.getId()));

        List<Comment> result = jpaQuery.fetch();

        return result;
    }
}
