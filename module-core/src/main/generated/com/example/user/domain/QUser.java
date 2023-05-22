package com.example.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.example.db.user.domain.User;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1662407939L;

    public static final QUser user = new QUser("user");

    public final StringPath id = createString("id");

    public final StringPath isDelete = createString("isDelete");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> modifyIdx = createNumber("modifyIdx", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath pwd = createString("pwd");

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> registerIdx = createNumber("registerIdx", Integer.class);

    public final StringPath role = createString("role");

    public final NumberPath<Integer> userIdx = createNumber("userIdx", Integer.class);

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

