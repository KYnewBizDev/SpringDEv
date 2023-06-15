package com.example.db.board.repository;

import com.example.db.board.domain.Board;
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
  public Page<Board> findLimit(BoardSearchDto search, Pageable pageable){
    List<Board> content = boardMapper.findLimit(search, pageable);
    Long total = boardMapper.findLimitCount(search);
    return new PageImpl<>(content, pageable, total);
  }

  // 뷰
  public Optional<Board> findByBoardId(String table, Long boardIdx, String isDelete) {
    return boardMapper.findByBoardId(table, boardIdx, isDelete);
  }

  // 조회수
  public void editHit(String table, Long boardIdx) {
    boardMapper.editHit(table, boardIdx);
  }

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

  // 상단고정/노출 적용
  public Long editTopOpen(String table, BoardDto boardDto) {
    boardMapper.editTopOpen(table, boardDto);
    return boardDto.getBoardIdx();
  }
}
