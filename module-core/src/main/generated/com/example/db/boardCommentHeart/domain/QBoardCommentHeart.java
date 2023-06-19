package com.example.db.boardCommentHeart.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardCommentHeart is a Querydsl query type for BoardCommentHeart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardCommentHeart extends EntityPathBase<BoardCommentHeart> {

    private static final long serialVersionUID = 1221601603L;

    public static final QBoardCommentHeart boardCommentHeart = new QBoardCommentHeart("boardCommentHeart");

    public final NumberPath<Long> boardCommentHeartIdx = createNumber("boardCommentHeartIdx", Long.class);

    public final NumberPath<Long> boardCommentIdx = createNumber("boardCommentIdx", Long.class);

    public final NumberPath<Long> boardGroupIdx = createNumber("boardGroupIdx", Long.class);

    public final StringPath isDelete = createString("isDelete");

    public final NumberPath<Long> memberIdx = createNumber("memberIdx", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> modifyIdx = createNumber("modifyIdx", Long.class);

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> registerIdx = createNumber("registerIdx", Long.class);

    public QBoardCommentHeart(String variable) {
        super(BoardCommentHeart.class, forVariable(variable));
    }

    public QBoardCommentHeart(Path<? extends BoardCommentHeart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardCommentHeart(PathMetadata metadata) {
        super(BoardCommentHeart.class, metadata);
    }

}

