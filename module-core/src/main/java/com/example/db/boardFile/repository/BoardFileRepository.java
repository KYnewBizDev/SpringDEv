package com.example.db.boardFile.repository;

import com.example.db.boardFile.domain.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
  // 뷰
  Optional<BoardFile> findByBoardGroupIdxAndBoardIdxAndSortAndIsDelete(Long boardGroupIdx, Long boardIdx, Integer sort, String isDelete);
}
