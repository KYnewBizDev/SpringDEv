package com.example.db.boardCommentFile.repository;

import com.example.db.boardCommentFile.domain.BoardCommentFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.db.boardCommentFile.domain.QBoardCommentFile.boardCommentFile;

@Repository
@RequiredArgsConstructor
public class BoardCommentFileQueryRepository {
  private final JPAQueryFactory query;

  public List<BoardCommentFile> findAll(Long boardGroupIdx, Long boardCommentIdx, String isDelete){
    return query
        .selectFrom(boardCommentFile)
        .where(
            boardCommentFile.isDelete.eq(isDelete)
            , boardCommentFile.boardGroupIdx.eq(boardGroupIdx)
            , boardCommentFile.boardCommentIdx.eq(boardCommentIdx)
        )
        .orderBy(boardCommentFile.sort.asc())
        .fetch();
  }
}
