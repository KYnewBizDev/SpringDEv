package com.example.db.auth.repository;

import com.example.db.auth.domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
  List<Auth> findAllByIsDelete(String isDelete);
}
