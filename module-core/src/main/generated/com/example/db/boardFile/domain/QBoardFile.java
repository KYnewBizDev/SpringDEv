package com.example.db.boardFile.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardFile is a Querydsl query type for BoardFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardFile extends EntityPathBase<BoardFile> {

    private static final long serialVersionUID = 310321891L;

    public static final QBoardFile boardFile = new QBoardFile("boardFile");

    public final NumberPath<Long> boardFileIdx = createNumber("boardFileIdx", Long.class);

    public final NumberPath<Long> boardGroupIdx = createNumber("boardGroupIdx", Long.class);

    public final NumberPath<Long> boardIdx = createNumber("boardIdx", Long.class);

    public final StringPath fileName = createString("fileName");

    public final StringPath isDelete = createString("isDelete");

    public final StringPath isMobile = createString("isMobile");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> modifyIdx = createNumber("modifyIdx", Long.class);

    public final StringPath originalFileName = createString("originalFileName");

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> registerIdx = createNumber("registerIdx", Long.class);

    public final NumberPath<Integer> sort = createNumber("sort", Integer.class);

    public QBoardFile(String variable) {
        super(BoardFile.class, forVariable(variable));
    }

    public QBoardFile(Path<? extends BoardFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardFile(PathMetadata metadata) {
        super(BoardFile.class, metadata);
    }

}

