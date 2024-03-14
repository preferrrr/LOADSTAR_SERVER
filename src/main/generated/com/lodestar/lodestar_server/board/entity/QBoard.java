package com.lodestar.lodestar_server.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -261910384L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final com.lodestar.lodestar_server.common.QBaseEntity _super = new com.lodestar.lodestar_server.common.QBaseEntity(this);

    public final NumberPath<Integer> bookmarkCount = createNumber("bookmarkCount", Integer.class);

    public final ListPath<com.lodestar.lodestar_server.bookmark.entity.Bookmark, com.lodestar.lodestar_server.bookmark.entity.QBookmark> bookmarks = this.<com.lodestar.lodestar_server.bookmark.entity.Bookmark, com.lodestar.lodestar_server.bookmark.entity.QBookmark>createList("bookmarks", com.lodestar.lodestar_server.bookmark.entity.Bookmark.class, com.lodestar.lodestar_server.bookmark.entity.QBookmark.class, PathInits.DIRECT2);

    public final ListPath<com.lodestar.lodestar_server.comment.entity.Comment, com.lodestar.lodestar_server.comment.entity.QComment> comments = this.<com.lodestar.lodestar_server.comment.entity.Comment, com.lodestar.lodestar_server.comment.entity.QComment>createList("comments", com.lodestar.lodestar_server.comment.entity.Comment.class, com.lodestar.lodestar_server.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<com.lodestar.lodestar_server.hashtag.entity.BoardHashtag, com.lodestar.lodestar_server.hashtag.entity.QBoardHashtag> hashtags = this.<com.lodestar.lodestar_server.hashtag.entity.BoardHashtag, com.lodestar.lodestar_server.hashtag.entity.QBoardHashtag>createList("hashtags", com.lodestar.lodestar_server.hashtag.entity.BoardHashtag.class, com.lodestar.lodestar_server.hashtag.entity.QBoardHashtag.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath status = _super.status;

    public final StringPath title = createString("title");

    public final com.lodestar.lodestar_server.user.entity.QUser user;

    public final NumberPath<Integer> view = createNumber("view", Integer.class);

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.lodestar.lodestar_server.user.entity.QUser(forProperty("user")) : null;
    }

}

