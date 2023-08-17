package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    private final JdbcTemplate jdbcTemplate;

    private static QBoard board = QBoard.board;
    private static QUser user = QUser.user;
    private static QBoardHashtag hashtag = QBoardHashtag.boardHashtag;
    private static QComment comment = QComment.comment;
    private static QBookmark bookmark = QBookmark.bookmark;

    @Override
    public List<Board> getBoardList(Pageable pageable, String[] hashtags) {

        JPAQuery<Long> getIdsQuery;

        //TODO : fetch join과 pagination 같이 사용할 수 없음 => 결국 id먼저 조회하는 걸로 돌아가야함.
        if(hashtags.length == 0) {
            getIdsQuery = jpaQueryFactory
                    .select(board.id)
                    .from(board)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize());
        } else {
            getIdsQuery = jpaQueryFactory
                    .select(board.id)
                    .distinct()
                    .from(board)
                    .leftJoin(board.hashtag,hashtag)
                    .where(hashtag.boardHashtagId.hashtagName.in(hashtags))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize());

        }

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(board.getType(), board.getMetadata());
            getIdsQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        List<Long> Ids = getIdsQuery.fetch();

        JPAQuery<Board> getBoardsQuery = jpaQueryFactory
                .selectFrom(board)
                .distinct()
                .leftJoin(board.hashtag, hashtag).fetchJoin()
                .join(board.user, user).fetchJoin()
                .where(board.id.in(Ids));

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(board.getType(), board.getMetadata());
            getBoardsQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        List<Board> result = getBoardsQuery.fetch();


        return result;
    }

    @Override
    public Optional<Board> getBoardWithHashAndComById(Long boardId) {

        JPAQuery<Board> jpaQuery = jpaQueryFactory
                .selectFrom(board)
                .distinct()
                .leftJoin(board.hashtag, hashtag).fetchJoin()
                .leftJoin(board.comments, comment)
                .where(board.id.eq(boardId));

        Board board = jpaQuery.fetchOne();

        return Optional.ofNullable(board);
    }


    @Override
    public Optional<Board> getBoardWithHashtagsById(Long boardId) {

        JPAQuery<Board> jpaQuery = jpaQueryFactory
                .selectFrom(board)
                .distinct()
                .leftJoin(board.hashtag, hashtag).fetchJoin()
                .where(board.id.eq(boardId));

        Board board = jpaQuery.fetchOne();

        return Optional.ofNullable(board);
    }

    @Override
    public List<Board> getMyBoardList(User me, Pageable pageable) {

        JPAQuery<Board> getBoardsQuery = jpaQueryFactory
                .select(board)
                .distinct()
                .from(board)
                .leftJoin(board.hashtag, hashtag)
                .join(board.user, user).fetchJoin()
                .where(board.user.id.eq(me.getId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(board.getType(), board.getMetadata());
            getBoardsQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        List<Board> result = getBoardsQuery.fetch();

        return result;
    }

    @Override
    public List<Board> getMyBookmarkBoardList(User me, Pageable pageable) {

        JPAQuery<Board> getBoardsQuery = jpaQueryFactory
                .selectFrom(board)
                .distinct()
                .leftJoin(board.hashtag, hashtag)
                .join(board.user, user).fetchJoin() // to one이니까 한 번에 가져와.
                .join(board.bookmarks, bookmark)
                .where(bookmark.user.id.eq(me.getId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(board.getType(), board.getMetadata());
            getBoardsQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        List<Board> result = getBoardsQuery.fetch();

        return result;
    }

    @Override
    public List<Board> getMyCommentBoardList(User me, Pageable pageable) {

        //TODO : subquery는 성능에 좋지 않음. subquery->query가 아닌 query->subquery. 생각과 반대로 수행됨
        // 다른 방법생각해야함
        JPAQuery<Board> getBoardsQuery = jpaQueryFactory
                .selectFrom(board)
                .distinct()
                .leftJoin(board.hashtag, hashtag)
                .join(board.user, user).fetchJoin() // to one이니까 한 번에 가져와.
                .where(board.id.in(JPAExpressions
                                .select(comment.board.id)
                                .distinct()
                                .from(comment)
                                .where(comment.user.id.eq(me.getId()))))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(board.getType(), board.getMetadata());
            getBoardsQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        List<Board> result = getBoardsQuery.fetch();

        return result;
    }


//    private BooleanBuilder containHashtags(String[] hashtags) {
//        BooleanBuilder builder = new BooleanBuilder();
//        QBoardHashtag hashtag = QBoardHashtag.boardHashtag;
//
//        for(String hashtagName : hashtags) {
//            builder.and(hashtag.hashtagName.eq(hashtagName));
//        }
//
//        return builder;
//    }

}
