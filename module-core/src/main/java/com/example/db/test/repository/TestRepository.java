package com.example.db.test.repository;

import com.example.db.test.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
  // 뷰
  Optional<Test> findByTestIdxAndIsDelete(Integer testIdx, String isDelete);
}
