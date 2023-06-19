package com.example.service;

import com.example.db.boardGroup.domain.BoardGroup;
import com.example.db.boardGroup.dto.BoardGroupDto;
import com.example.db.boardGroup.repository.BoardGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardGroupService {
  private final BoardGroupRepository boardGroupRepository;

  // 뷰
  @Transactional(readOnly = true)
  public BoardGroupDto getBoardGroupCode(String boardCode) {
    Optional<BoardGroup> boardGroup = boardGroupRepository.findByBoardCodeAndIsDelete(boardCode, "N");

    BoardGroupDto boardGroupDto = new BoardGroupDto();
    boardGroup.ifPresent(file -> BeanUtils.copyProperties(file, boardGroupDto));
    return boardGroupDto;
  }
}