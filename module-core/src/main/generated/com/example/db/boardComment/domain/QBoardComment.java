package com.example.db.boardComment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardComment is a Querydsl query type for BoardComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardComment extends EntityPathBase<BoardComment> {

    private static final long serialVersionUID = 27268301L;

    public static final QBoardComment boardComment = new QBoardComment("boardComment");

    public final NumberPath<Long> boardCommentIdx = createNumber("boardCommentIdx", Long.class);

    public final NumberPath<Long> boardGroupIdx = createNumber("boardGroupIdx", Long.class);

    public final NumberPath<Long> boardIdx = createNumber("boardIdx", Long.class);

    public final StringPath content = createString("content");

    public final NumberPath<Integer> heartNum = createNumber("heartNum", Integer.class);

    public final StringPath isChoose = createString("isChoose");

    public final StringPath isDelete = createString("isDelete");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> modifyIdx = createNumber("modifyIdx", Long.class);

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> registerIdx = createNumber("registerIdx", Long.class);

    public QBoardComment(String variable) {
        super(BoardComment.class, forVariable(variable));
    }

    public QBoardComment(Path<? extends BoardComment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardComment(PathMetadata metadata) {
        super(BoardComment.class, metadata);
    }

}

