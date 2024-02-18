package com.lodestar.lodestar_server.user.respository;

import com.lodestar.lodestar_server.career.entity.QCareer;
import com.lodestar.lodestar_server.user.entity.QUser;
import com.lodestar.lodestar_server.user.entity.User;
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
