package com.lodestar.lodestar_server.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMail is a Querydsl query type for Mail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMail extends EntityPathBase<Mail> {

    private static final long serialVersionUID = 474866709L;

    public static final QMail mail = new QMail("mail");

    public final StringPath authKey = createString("authKey");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QMail(String variable) {
        super(Mail.class, forVariable(variable));
    }

    public QMail(Path<? extends Mail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMail(PathMetadata metadata) {
        super(Mail.class, metadata);
    }

}

