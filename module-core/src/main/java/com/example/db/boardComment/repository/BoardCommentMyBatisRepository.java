package com.example.db.boardComment.repository;

import com.example.db.boardComment.dto.BoardCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class BoardCommentMyBatisRepository {
  private final BoardCommentMapper boardCommentMapper;

  // 리스트
  public Page<BoardCommentDto> findLimit(String table, Long boardIdx, String isDelete, Pageable pageable){
    List<BoardCommentDto> content = boardCommentMapper.findLimit(table+ "_comment", boardIdx, isDelete, pageable);
    Long total = boardCommentMapper.findLimitCount(table+ "_comment", boardIdx, isDelete);
    return new PageImpl<>(content, pageable, total);
  }

  // 뷰
  public Optional<BoardCommentDto> findByBoardCommentIdx(String table, Long boardCommentIdx, String isDelete) {
    return boardCommentMapper.findByBoardCommentIdx(table+ "_comment", boardCommentIdx, isDelete);
  }

  // 등록
  public Long addBoardComment(String table, BoardCommentDto boardCommentDto) {
    boardCommentMapper.addBoardComment(table+ "_comment", boardCommentDto);
    return boardCommentDto.getBoardCommentIdx();
  }

  // 수정
  public Long editBoardComment(String table, BoardCommentDto boardCommentDto) {
    boardCommentMapper.editBoardComment(table+ "_comment", boardCommentDto);
    return boardCommentDto.getBoardCommentIdx();
  }

  // 삭제
  public Long deleteBoardComment(String table, BoardCommentDto boardCommentDto) {
    boardCommentMapper.deleteBoardComment(table+ "_comment", boardCommentDto);
    return boardCommentDto.getBoardCommentIdx();
  }
}
