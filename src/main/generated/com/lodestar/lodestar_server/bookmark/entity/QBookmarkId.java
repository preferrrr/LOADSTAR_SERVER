package com.lodestar.lodestar_server.bookmark.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBookmarkId is a Querydsl query type for BookmarkId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBookmarkId extends BeanPath<BookmarkId> {

    private static final long serialVersionUID = -874696239L;

    public static final QBookmarkId bookmarkId = new QBookmarkId("bookmarkId");

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QBookmarkId(String variable) {
        super(BookmarkId.class, forVariable(variable));
    }

    public QBookmarkId(Path<? extends BookmarkId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBookmarkId(PathMetadata metadata) {
        super(BookmarkId.class, metadata);
    }

}

