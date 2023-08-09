package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Board;
import com.lodestar.lodestar_server.entity.QCareer;
import com.lodestar.lodestar_server.entity.QUser;
import com.lodestar.lodestar_server.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    private static QUser user = QUser.user;
    private static QCareer career = QCareer.career;

    @Override
    public Optional<User> findUserWithCareersById(Long userId) {

        JPAQuery<User> jpaQuery = jpaQueryFactory
                .selectFrom(user)
                .distinct()
                .leftJoin(user.careers, career).fetchJoin()
                .where(user.id.eq(userId));

        User user = jpaQuery.fetchOne();

        return Optional.ofNullable(user);

    }
}
