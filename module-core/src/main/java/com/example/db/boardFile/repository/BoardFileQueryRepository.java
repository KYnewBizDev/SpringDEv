package com.example.db.boardFile.repository;

import com.example.db.boardFile.domain.BoardFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.db.boardFile.domain.QBoardFile.boardFile;

@Repository
@RequiredArgsConstructor
public class BoardFileQueryRepository {
  private final JPAQueryFactory query;

  public List<BoardFile> findAll(Long boardGroupIdx, Long boardIdx, String isDelete){
    return query
        .selectFrom(boardFile)
        .where(
            boardFile.isDelete.eq(isDelete)
            , boardFile.boardGroupIdx.eq(boardGroupIdx)
            , boardFile.boardIdx.eq(boardIdx)
        )
        .orderBy(boardFile.sort.asc())
        .fetch();
  }
}
