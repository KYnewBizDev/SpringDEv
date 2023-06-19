package com.example.db.board.repository;
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
    List<BoardDto> findLimit(@Param("table") String table, @Param("search") BoardSearchDto search, @Param("isDelete") String isDelete, @Param("pageable") Pageable pageable);
    Long findLimitCount(@Param("table") String table, @Param("search") BoardSearchDto search, @Param("isDelete") String isDelete);

    // 뷰
    Optional<BoardDto>
    findByBoardIdx(@Param("table") String table, @Param("boardIdx") Long boardIdx, @Param("isDelete") String isDelete);

    // 뷰 (답변)
    Optional<BoardDto>
    findByParentIdx(@Param("table") String table, @Param("parentIdx") Long parentIdx, @Param("isDelete") String isDelete);

    // 조회수
    void editHit(@Param("table") String table, @Param("boardIdx") Long boardIdx);

    // 상단고정/노출 적용
    void editTopOpen(@Param("table") String table, @Param("boardDto") BoardDto boardDto);


    //region /** crud **/
    // 등록
    void addBoard(@Param("table") String table, @Param("boardDto") BoardDto boardDto);

    // 수정
    void editBoard(@Param("table") String table, @Param("boardDto") BoardDto boardDto);

    // 삭제
    void deleteBoard(@Param("table") String table, @Param("boardDto") BoardDto boardDto);
    //endregion /** crud **/
}
