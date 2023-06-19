package com.example.db.boardCommentFile.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardCommentFile is a Querydsl query type for BoardCommentFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardCommentFile extends EntityPathBase<BoardCommentFile> {

    private static final long serialVersionUID = 1132386693L;

    public static final QBoardCommentFile boardCommentFile = new QBoardCommentFile("boardCommentFile");

    public final NumberPath<Long> boardCommentFileIdx = createNumber("boardCommentFileIdx", Long.class);

    public final NumberPath<Long> boardCommentIdx = createNumber("boardCommentIdx", Long.class);

    public final NumberPath<Long> boardGroupIdx = createNumber("boardGroupIdx", Long.class);

    public final StringPath fileName = createString("fileName");

    public final StringPath isDelete = createString("isDelete");

    public final StringPath isMobile = createString("isMobile");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> modifyIdx = createNumber("modifyIdx", Long.class);

    public final StringPath originalFileName = createString("originalFileName");

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> registerIdx = createNumber("registerIdx", Long.class);

    public final NumberPath<Integer> sort = createNumber("sort", Integer.class);

    public QBoardCommentFile(String variable) {
        super(BoardCommentFile.class, forVariable(variable));
    }

    public QBoardCommentFile(Path<? extends BoardCommentFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardCommentFile(PathMetadata metadata) {
        super(BoardCommentFile.class, metadata);
    }

}

