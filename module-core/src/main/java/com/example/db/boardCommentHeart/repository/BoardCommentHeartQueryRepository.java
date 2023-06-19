package com.example.db.boardCommentHeart.repository;

import com.example.db.boardCommentHeart.domain.BoardCommentHeart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.db.boardCommentHeart.domain.QBoardCommentHeart.boardCommentHeart;

@Repository
@RequiredArgsConstructor
public class BoardCommentHeartQueryRepository {
  private final JPAQueryFactory query;

  public List<BoardCommentHeart> findAll(Long boardGroupIdx, Long boardCommentIdx, String isDelete){
    return query
        .selectFrom(boardCommentHeart)
        .where(
            boardCommentHeart.isDelete.eq(isDelete)
            , boardCommentHeart.boardGroupIdx.eq(boardGroupIdx)
            , boardCommentHeart.boardCommentIdx.eq(boardCommentIdx)
        )
        .orderBy(boardCommentHeart.boardCommentHeartIdx.desc())
        .fetch();
  }
}
