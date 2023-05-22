package com.example.db.test.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTest is a Querydsl query type for Test
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTest extends EntityPathBase<Test> {

    private static final long serialVersionUID = -1764949441L;

    public static final QTest test = new QTest("test");

    public final StringPath fileName = createString("fileName");

    public final StringPath isDelete = createString("isDelete");

    public final StringPath isOpen = createString("isOpen");

    public final StringPath memo = createString("memo");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> modifyIdx = createNumber("modifyIdx", Integer.class);

    public final StringPath originalFileName = createString("originalFileName");

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> registerIdx = createNumber("registerIdx", Integer.class);

    public final NumberPath<Integer> testIdx = createNumber("testIdx", Integer.class);

    public final StringPath testName = createString("testName");

    public final StringPath testPwd = createString("testPwd");

    public QTest(String variable) {
        super(Test.class, forVariable(variable));
    }

    public QTest(Path<? extends Test> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTest(PathMetadata metadata) {
        super(Test.class, metadata);
    }

}

