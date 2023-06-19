package com.example.db.board.repository;

import com.example.db.board.dto.BoardDto;
import com.example.db.board.dto.BoardSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class BoardMyBatisRepository {
  private final BoardMapper boardMapper;

  // 리스트
  public Page<BoardDto> findLimit(String table, BoardSearchDto search, String isDelete, Pageable pageable){
    List<BoardDto> content = boardMapper.findLimit(table, search, isDelete, pageable);
    Long total = boardMapper.findLimitCount(table, search, isDelete);
    return new PageImpl<>(content, pageable, total);
  }

  // 뷰
  public Optional<BoardDto> findByBoardIdx(String table, Long boardIdx, String isDelete) {
    return boardMapper.findByBoardIdx(table, boardIdx, isDelete);
  }

  // 뷰 (답변)
  public Optional<BoardDto> findByParentIdx(String table, Long parentIdx, String isDelete) {
    return boardMapper.findByParentIdx(table, parentIdx, isDelete);
  }

  // 조회수
  public void editHit(String table, Long boardIdx) {
    boardMapper.editHit(table, boardIdx);
  }

  // 상단고정/노출 적용
  public Long editTopOpen(String table, BoardDto boardDto) {
    boardMapper.editTopOpen(table, boardDto);
    return boardDto.getBoardIdx();
  }


  //region /** crud **/
  // 등록
  public Long addBoard(String table, BoardDto boardDto) {
    boardMapper.addBoard(table, boardDto);
    return boardDto.getBoardIdx();
  }

  // 수정
  public Long editBoard(String table, BoardDto boardDto) {
    boardMapper.editBoard(table, boardDto);
    return boardDto.getBoardIdx();
  }

  // 삭제
  public Long deleteBoard(String table, BoardDto boardDto) {
    boardMapper.deleteBoard(table, boardDto);
    return boardDto.getBoardIdx();
  }
  //endregion /** crud **/
}
