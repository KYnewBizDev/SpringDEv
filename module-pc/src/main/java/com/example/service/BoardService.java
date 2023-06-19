package com.example.service;


import com.example.db.board.dto.BoardDto;
import com.example.db.board.dto.BoardSearchDto;
import com.example.db.board.repository.*;
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
public class BoardService {
  private final BoardMyBatisRepository boardMyBatisRepository;

  // 리스트
  @Transactional(readOnly = true)
  public Page<BoardDto> getBoardBasicList(String table, int page, int perPage, BoardSearchDto boardSearchDto) {
    Pageable pageable = PageRequest.of(page, perPage);
    return boardMyBatisRepository.findLimit(table, boardSearchDto, "N", pageable);
  }

  // 뷰
  @Transactional(readOnly = true)
  public BoardDto getBoard(String table, Long boardIdx) {
    Optional<BoardDto> board = boardMyBatisRepository.findByBoardIdx(table, boardIdx, "N");

    BoardDto boardDto = new BoardDto();
    board.ifPresent(value -> BeanUtils.copyProperties(value, boardDto));
    return boardDto;
  }

  // 뷰 (답변)
  @Transactional(readOnly = true)
  public BoardDto getBoardReply(String table, Long parentIdx) {
    Optional<BoardDto> board = boardMyBatisRepository.findByParentIdx(table, parentIdx, "N");

    BoardDto boardDto = new BoardDto();
    board.ifPresent(value -> BeanUtils.copyProperties(value, boardDto));
    return boardDto;
  }

  // 조회수
  @Transactional
  public void editHit(String table, Long boardIdx) {
    boardMyBatisRepository.editHit(table, boardIdx);
  }

  // 상단고정/노출 적용
  @Transactional
  public Long editTopOpen(String table, BoardDto boardDto) {
    return boardMyBatisRepository.editTopOpen(table, boardDto);
  }


  //region /** crud **/
  // 등록
  @Transactional
  public Long addBoard(String table, BoardDto boardDto) {
    return boardMyBatisRepository.addBoard(table, boardDto);
  }
  
  // 수정
  @Transactional
  public Long editBoard(String table, BoardDto boardDto) {
    return boardMyBatisRepository.editBoard(table, boardDto);
  }
  
  // 삭제
  @Transactional
  public Long deleteBoard(String table, BoardDto boardDto) {
    return boardMyBatisRepository.deleteBoard(table, boardDto);
  }
  //endregion /** crud **/
}
