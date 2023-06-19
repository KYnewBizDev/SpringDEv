package com.example.service;


import com.example.db.boardComment.dto.BoardCommentDto;
import com.example.db.boardComment.repository.BoardCommentMyBatisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardCommentService {
  private final BoardCommentMyBatisRepository boardCommentMyBatisRepository;

  // 리스트
  @Transactional(readOnly = true)
  public Page<BoardCommentDto> getBoardCommentList(String table, Long boardIdx, int page, int perPage) {
    Pageable pageable = PageRequest.of(page, perPage);
    return boardCommentMyBatisRepository.findLimit(table, boardIdx, "N", pageable);
  }

  // 뷰
  @Transactional(readOnly = true)
  public BoardCommentDto getBoardComment(String table, Long boardCommentIdx) {
    Optional<BoardCommentDto> board = boardCommentMyBatisRepository.findByBoardCommentIdx(table, boardCommentIdx, "N");

    BoardCommentDto boardCommentDto = new BoardCommentDto();
    board.ifPresent(value -> BeanUtils.copyProperties(value, boardCommentDto));
    return boardCommentDto;
  }

  // 등록
  @Transactional
  public Long addBoardComment(String table, BoardCommentDto boardCommentDto) {
    return boardCommentMyBatisRepository.addBoardComment(table, boardCommentDto);
  }
  
  // 수정
  @Transactional
  public Long editBoardComment(String table, BoardCommentDto boardCommentDto) {
    return boardCommentMyBatisRepository.editBoardComment(table, boardCommentDto);
  }
  
  // 삭제
  @Transactional
  public Long deleteBoardComment(String table, BoardCommentDto boardCommentDto) {
    return boardCommentMyBatisRepository.deleteBoardComment(table, boardCommentDto);
  }
}
