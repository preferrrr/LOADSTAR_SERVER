package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Board> getBoardList(Pageable pageable, String[] hashtags) {

        QBoard board = QBoard.board;
        QBoardHashtag hashtag = QBoardHashtag.boardHashtag;
        QUser user = QUser.user;

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
                    .join(board.hashtag,hashtag)
                    .where(hashtag.hashtagName.in(hashtags))
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
                .join(board.hashtag, hashtag).fetchJoin().distinct()
                .join(board.user, user).fetchJoin()
                .on(user.id.eq(board.user.id))
                .where(board.id.in(Ids));

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
