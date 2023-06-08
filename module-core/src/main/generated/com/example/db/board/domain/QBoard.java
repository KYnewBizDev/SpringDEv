package com.example.db.board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -956721821L;

    public static final QBoard board = new QBoard("board");

    public final NumberPath<Long> boardCommentIdx = createNumber("boardCommentIdx", Long.class);

    public final NumberPath<Long> boardGroupIdx = createNumber("boardGroupIdx", Long.class);

    public final NumberPath<Long> boardIdx = createNumber("boardIdx", Long.class);

    public final StringPath category1 = createString("category1");

    public final StringPath category2 = createString("category2");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final StringPath isAlltime = createString("isAlltime");

    public final StringPath isDelete = createString("isDelete");

    public final StringPath isHot = createString("isHot");

    public final StringPath isNew = createString("isNew");

    public final StringPath isOpen = createString("isOpen");

    public final StringPath isReply = createString("isReply");

    public final StringPath isTop = createString("isTop");

    public final StringPath link1 = createString("link1");

    public final StringPath link1Target = createString("link1Target");

    public final StringPath link2 = createString("link2");

    public final StringPath link2Target = createString("link2Target");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> modifyIdx = createNumber("modifyIdx", Long.class);

    public final NumberPath<Long> parentIdx = createNumber("parentIdx", Long.class);

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> registerIdx = createNumber("registerIdx", Long.class);

    public final NumberPath<Integer> sort = createNumber("sort", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> topDate = createDateTime("topDate", java.time.LocalDateTime.class);

    public final StringPath voc = createString("voc");

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

