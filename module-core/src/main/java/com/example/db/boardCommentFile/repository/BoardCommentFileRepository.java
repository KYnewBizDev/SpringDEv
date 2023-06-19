package com.example.db.boardCommentFile.repository;

import com.example.db.boardCommentFile.domain.BoardCommentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardCommentFileRepository extends JpaRepository<BoardCommentFile, Long> {
  // 뷰
  Optional<BoardCommentFile> findByBoardGroupIdxAndBoardCommentIdxAndSortAndIsDelete(Long boardGroupIdx, Long boardCommentIdx, Integer sort, String isDelete);
}
