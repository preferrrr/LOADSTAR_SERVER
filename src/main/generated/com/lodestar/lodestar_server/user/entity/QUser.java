package com.lodestar.lodestar_server.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1270827584L;

    public static final QUser user = new QUser("user");

    public final com.lodestar.lodestar_server.config.QBaseEntity _super = new com.lodestar.lodestar_server.config.QBaseEntity(this);

    public final ListPath<com.lodestar.lodestar_server.board.entity.Board, com.lodestar.lodestar_server.board.entity.QBoard> boards = this.<com.lodestar.lodestar_server.board.entity.Board, com.lodestar.lodestar_server.board.entity.QBoard>createList("boards", com.lodestar.lodestar_server.board.entity.Board.class, com.lodestar.lodestar_server.board.entity.QBoard.class, PathInits.DIRECT2);

    public final ListPath<com.lodestar.lodestar_server.bookmark.entity.Bookmark, com.lodestar.lodestar_server.bookmark.entity.QBookmark> bookmarks = this.<com.lodestar.lodestar_server.bookmark.entity.Bookmark, com.lodestar.lodestar_server.bookmark.entity.QBookmark>createList("bookmarks", com.lodestar.lodestar_server.bookmark.entity.Bookmark.class, com.lodestar.lodestar_server.bookmark.entity.QBookmark.class, PathInits.DIRECT2);

    public final ListPath<com.lodestar.lodestar_server.career.entity.Career, com.lodestar.lodestar_server.career.entity.QCareer> careers = this.<com.lodestar.lodestar_server.career.entity.Career, com.lodestar.lodestar_server.career.entity.QCareer>createList("careers", com.lodestar.lodestar_server.career.entity.Career.class, com.lodestar.lodestar_server.career.entity.QCareer.class, PathInits.DIRECT2);

    public final ListPath<com.lodestar.lodestar_server.comment.entity.Comment, com.lodestar.lodestar_server.comment.entity.QComment> comments = this.<com.lodestar.lodestar_server.comment.entity.Comment, com.lodestar.lodestar_server.comment.entity.QComment>createList("comments", com.lodestar.lodestar_server.comment.entity.Comment.class, com.lodestar.lodestar_server.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath password = createString("password");

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final StringPath status = _super.status;

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

