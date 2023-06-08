package com.example.service;


import com.example.db.board.dto.BoardDto;
import com.example.db.board.dto.BoardSearchDto;
import com.example.db.board.repository.*;
import com.example.db.board.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardMyBatisRepository boardMyBatisRepository;

  // 리스트
  @Transactional(readOnly = true)
  public Page<BoardDto> getBasicList(String table, int page, int perPage, String searchType, String searchWord, String startDate, String endDate, String isOpen, String category1, String category2) {
    Pageable pageable = PageRequest.of(page, perPage);

    BoardSearchDto search = new BoardSearchDto(table, searchType, searchWord, startDate, endDate, isOpen, category1, category2, "N");
    Page<Board> boardList = boardMyBatisRepository.findLimit(search, pageable);

    return boardList.map(board-> {
      BoardDto boardDto = new BoardDto();
      BeanUtils.copyProperties(board, boardDto);
      return boardDto;
    });
  }

  // 뷰
  @Transactional(readOnly = true)
  public BoardDto getBoard(String table, Long boardIdx) {
    Board board = boardMyBatisRepository.findByBoardId(table, boardIdx, "N");

    BoardDto boardDto = new BoardDto();
    BeanUtils.copyProperties(board,boardDto);
    return boardDto;
  }

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
}
