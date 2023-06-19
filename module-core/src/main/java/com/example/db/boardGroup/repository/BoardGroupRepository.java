package com.example.db.boardGroup.repository;

import com.example.db.boardGroup.domain.BoardGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardGroupRepository extends JpaRepository<BoardGroup, Long> {
  // 뷰
  Optional<BoardGroup> findByBoardCodeAndIsDelete(String boardCode, String isDelete);
}
