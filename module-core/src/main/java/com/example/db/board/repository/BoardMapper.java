package com.example.db.board.repository;
import com.example.db.board.domain.Board;
import com.example.db.board.dto.BoardDto;
import com.example.db.board.dto.BoardSearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    // 리스트
    List<Board> findLimit(@Param("search") BoardSearchDto search, @Param("pageable") Pageable pageable);
    Long findLimitCount(@Param("search") BoardSearchDto search);

    // 뷰
    Optional<Board>
    findByBoardId(@Param("table") String table, @Param("boardIdx") Long boardIdx, @Param("isDelete") String isDelete);

    // 조회수
    void editHit(@Param("table") String table, @Param("boardIdx") Long boardIdx);

    // 등록
    void addBoard(@Param("table") String table, @Param("boardDto") BoardDto boardDto);

    // 수정
    void editBoard(@Param("table") String table, @Param("boardDto") BoardDto boardDto);

    // 삭제
    void deleteBoard(@Param("table") String table, @Param("boardDto") BoardDto boardDto);
}
