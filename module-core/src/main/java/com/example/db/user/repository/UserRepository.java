package com.example.db.user.repository;

import com.example.db.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  // 뷰
  Optional<User> findByUserIdxAndIsDelete(Integer userIdx, String isDelete);

  // 로그인
  Optional<User> findByIdAndIsDelete(String id, String isDelete);
}
