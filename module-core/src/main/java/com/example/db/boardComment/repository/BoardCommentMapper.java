package com.example.db.boardComment.repository;
import com.example.db.boardComment.dto.BoardCommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardCommentMapper {
    // 리스트
    List<BoardCommentDto> findLimit(@Param("table") String table, @Param("boardIdx") Long boardIdx, @Param("isDelete") String isDelete, @Param("pageable") Pageable pageable);
    Long findLimitCount(@Param("table") String table, @Param("boardIdx") Long boardIdx, @Param("isDelete") String isDelete);

    // 뷰
    Optional<BoardCommentDto>
    findByBoardCommentIdx(@Param("table") String table, @Param("boardCommentIdx") Long boardCommentIdx, @Param("isDelete") String isDelete);

    // 등록
    void addBoardComment(@Param("table") String table, @Param("boardCommentDto") BoardCommentDto boardCommentDto);

    // 수정
    void editBoardComment(@Param("table") String table, @Param("boardCommentDto") BoardCommentDto boardCommentDto);

    // 삭제
    void deleteBoardComment(@Param("table") String table, @Param("boardCommentDto") BoardCommentDto boardCommentDto);
}
