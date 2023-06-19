package com.example.db.boardCommentHeart.repository;

import com.example.db.boardCommentHeart.domain.BoardCommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardCommentHeartRepository extends JpaRepository<BoardCommentHeart, Long> {
}
