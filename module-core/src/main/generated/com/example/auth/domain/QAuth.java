package com.example.auth.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.example.db.auth.domain.Auth;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuth is a Querydsl query type for Auth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuth extends EntityPathBase<Auth> {

    private static final long serialVersionUID = 1648317757L;

    public static final QAuth auth = new QAuth("auth");

    public final NumberPath<Integer> authIdx = createNumber("authIdx", Integer.class);

    public final StringPath authority = createString("authority");

    public final StringPath isDelete = createString("isDelete");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> modifyIdx = createNumber("modifyIdx", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> registerIdx = createNumber("registerIdx", Integer.class);

    public final StringPath role = createString("role");

    public QAuth(String variable) {
        super(Auth.class, forVariable(variable));
    }

    public QAuth(Path<? extends Auth> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuth(PathMetadata metadata) {
        super(Auth.class, metadata);
    }

}

