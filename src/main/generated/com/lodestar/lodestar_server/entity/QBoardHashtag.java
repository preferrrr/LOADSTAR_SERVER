package com.lodestar.lodestar_server.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardHashtag is a Querydsl query type for BoardHashtag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardHashtag extends EntityPathBase<BoardHashtag> {

    private static final long serialVersionUID = 1672975972L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardHashtag boardHashtag = new QBoardHashtag("boardHashtag");

    public final QBoard board;

    public final QBoardHashtagId id;

    public QBoardHashtag(String variable) {
        this(BoardHashtag.class, forVariable(variable), INITS);
    }

    public QBoardHashtag(Path<? extends BoardHashtag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardHashtag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardHashtag(PathMetadata metadata, PathInits inits) {
        this(BoardHashtag.class, metadata, inits);
    }

    public QBoardHashtag(Class<? extends BoardHashtag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.id = inits.isInitialized("id") ? new QBoardHashtagId(forProperty("id")) : null;
    }

}

