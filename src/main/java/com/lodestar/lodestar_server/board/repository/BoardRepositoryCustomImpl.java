package com.lodestar.lodestar_server.board.repository;

import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.board.entity.QBoard;
import com.lodestar.lodestar_server.bookmark.entity.QBookmark;
import com.lodestar.lodestar_server.comment.entity.QComment;
import com.lodestar.lodestar_server.hashtag.entity.QBoardHashtag;
import com.lodestar.lodestar_server.user.entity.QUser;
import com.lodestar.lodestar_server.user.entity.User;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    private static QBoard board = QBoard.board;
    private static QUser user = QUser.user;
    private static QBoardHashtag hashtag = QBoardHashtag.boardHashtag;
    private static QComment comment = QComment.comment;
    private static QBookmark bookmark = QBookmark.bookmark;

    @Override
    public List<Board> getBoardList(Pageable pageable, String[] hashtags) {

        JPAQuery<Board> getBoardsQuery;
        if(hashtags.length == 0) {
            getBoardsQuery = jpaQueryFactory
                    .select(board)
                    .distinct()
                    .from(board)
                    .leftJoin(board.hashtags, hashtag)
                    // to many는 jetch join할 때 페이징 불가. 모두 어플리케이션 메모리로 가지고 와서 스프링에서 페이징함.
                    // 만약 10만 건이면 10만 개 모두 올라오기 때문에 절대 안됨, N+1은 jpa batch가 해결.
                    .join(board.user, user).fetchJoin()
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize());
        } else {
            getBoardsQuery = jpaQueryFactory
                    .select(board)
                    .distinct()
                    .from(board)
                    .leftJoin(board.hashtags,hashtag)
                    .join(board.user, user).fetchJoin()
                    .where(hashtag.id.hashtagName.in(hashtags))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize());

        }

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
                .leftJoin(board.hashtags, hashtag).fetchJoin()
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
                .leftJoin(board.hashtags, hashtag).fetchJoin()
                .where(board.id.eq(boardId));

        Board board = jpaQuery.fetchOne();

        return Optional.ofNullable(board);
    }

    @Override
    public List<Board> getMyBoardList(User me, Pageable pageable) {

//        JPAQuery<Board> getBoardsQuery = jpaQueryFactory
//                .select(board)
//                .distinct()
//                .from(board)
//                .leftJoin(board.hashtag, hashtag)
//                .join(board.user, user).fetchJoin()
//                .where(board.user.id.eq(me.getId()))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize());


        JPAQuery<Board> getBoardsQuery = jpaQueryFactory
                .select(board)
                .distinct()
                .from(board)
                .leftJoin(board.hashtags, hashtag)
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

//        JPAQuery<Board> getBoardsQuery = jpaQueryFactory
//                .selectFrom(board)
//                .distinct()
//                .leftJoin(board.hashtag, hashtag)
//                .join(board.user, user).fetchJoin() // to one이니까 한 번에 가져와.
//                .join(board.bookmarks, bookmark)
//                .where(bookmark.user.id.eq(me.getId()))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize());

        JPAQuery<Board> getBoardsQuery = jpaQueryFactory
                .selectFrom(board)
                .distinct()
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
//        JPAQuery<Board> getBoardsQuery = jpaQueryFactory
//                .selectFrom(board)
//                .distinct()
//                .leftJoin(board.hashtags, hashtag)
//                .join(board.user, user).fetchJoin() // to one이니까 한 번에 가져와.
//                .where(board.id.in(JPAExpressions
//                                .select(comment.board.id)
//                                .distinct()
//                                .from(comment)
//                                .where(comment.user.id.eq(me.getId()))))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize());

        JPAQuery<Board> getBoardsQuery = jpaQueryFactory
                .selectFrom(board).distinct()
                .leftJoin(board.hashtags, hashtag)
                .join(board.user, user).fetchJoin()
                .join(board.comments, comment)
                .where(comment.user.id.eq(me.getId()))
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
