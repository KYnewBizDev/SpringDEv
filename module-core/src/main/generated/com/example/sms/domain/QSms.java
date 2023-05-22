package com.example.sms.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.example.db.sms.domain.Sms;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSms is a Querydsl query type for Sms
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSms extends EntityPathBase<Sms> {

    private static final long serialVersionUID = 359951573L;

    public static final QSms sms = new QSms("sms");

    public final StringPath fileName = createString("fileName");

    public final StringPath isDelete = createString("isDelete");

    public final StringPath isOpen = createString("isOpen");

    public final StringPath memo = createString("memo");

    public final StringPath originalFileName = createString("originalFileName");

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> registerIdx = createNumber("registerIdx", Integer.class);

    public final NumberPath<Integer> testIdx = createNumber("testIdx", Integer.class);

    public final StringPath testName = createString("testName");

    public final StringPath testPwd = createString("testPwd");

    public QSms(String variable) {
        super(Sms.class, forVariable(variable));
    }

    public QSms(Path<? extends Sms> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSms(PathMetadata metadata) {
        super(Sms.class, metadata);
    }

}

