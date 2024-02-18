package com.lodestar.lodestar_server.hashtag.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardHashtagId is a Querydsl query type for BoardHashtagId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBoardHashtagId extends BeanPath<BoardHashtagId> {

    private static final long serialVersionUID = -1378109699L;

    public static final QBoardHashtagId boardHashtagId = new QBoardHashtagId("boardHashtagId");

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final StringPath hashtagName = createString("hashtagName");

    public QBoardHashtagId(String variable) {
        super(BoardHashtagId.class, forVariable(variable));
    }

    public QBoardHashtagId(Path<? extends BoardHashtagId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardHashtagId(PathMetadata metadata) {
        super(BoardHashtagId.class, metadata);
    }

}

