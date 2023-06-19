package com.example.db.boardGroup.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardGroup is a Querydsl query type for BoardGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardGroup extends EntityPathBase<BoardGroup> {

    private static final long serialVersionUID = 322087629L;

    public static final QBoardGroup boardGroup = new QBoardGroup("boardGroup");

    public final StringPath boardCode = createString("boardCode");

    public final NumberPath<Long> boardGroupIdx = createNumber("boardGroupIdx", Long.class);

    public final StringPath boardName = createString("boardName");

    public final NumberPath<Integer> fileNum = createNumber("fileNum", Integer.class);

    public final StringPath isComment = createString("isComment");

    public final StringPath isCommentHeart = createString("isCommentHeart");

    public final StringPath isDelete = createString("isDelete");

    public final StringPath isFile = createString("isFile");

    public final StringPath isHit = createString("isHit");

    public final StringPath isPersonal = createString("isPersonal");

    public final StringPath isReply = createString("isReply");

    public final StringPath isShare = createString("isShare");

    public final StringPath isTop = createString("isTop");

    public final StringPath isUse = createString("isUse");

    public final StringPath memo = createString("memo");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> modifyIdx = createNumber("modifyIdx", Long.class);

    public final StringPath orderby = createString("orderby");

    public final NumberPath<Integer> pageNum = createNumber("pageNum", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> registerIdx = createNumber("registerIdx", Long.class);

    public final StringPath skin = createString("skin");

    public QBoardGroup(String variable) {
        super(BoardGroup.class, forVariable(variable));
    }

    public QBoardGroup(Path<? extends BoardGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardGroup(PathMetadata metadata) {
        super(BoardGroup.class, metadata);
    }

}

