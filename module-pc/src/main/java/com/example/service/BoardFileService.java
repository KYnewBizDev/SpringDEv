package com.example.service;

import com.example.db.boardFile.domain.BoardFile;
import com.example.db.boardFile.dto.BoardFileDto;
import com.example.db.boardFile.repository.BoardFileQueryRepository;
import com.example.db.boardFile.repository.BoardFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardFileService {
  private final BoardFileRepository boardFileRepository;
  private final BoardFileQueryRepository boardFileQueryRepository;

  // 리스트
  @Transactional(readOnly = true)
  public List<BoardFileDto> getList(Long boardGroupIdx, Long boardIdx) {
    List<BoardFile> boardFileList = boardFileQueryRepository.findAllByIsDelete(boardGroupIdx, boardIdx, "N");
    List<BoardFileDto> boardFileDtoList = new ArrayList<>();

    for (BoardFile boardFile : boardFileList) {
      BoardFileDto boardFileDto = new BoardFileDto();
      BeanUtils.copyProperties(boardFile,boardFileDto);
      boardFileDtoList.add(boardFileDto);
    }
    return boardFileDtoList;
  }

  // 뷰
  @Transactional(readOnly = true)
  public BoardFileDto getBoardFile(Long boardGroupIdx, Long boardIdx, Integer sort) {
    Optional<BoardFile> boardFile = boardFileRepository.findTop1ByBoardGroupIdxAndBoardIdxAndSortAndIsDelete(boardGroupIdx, boardIdx, sort, "N");
    BoardFileDto boardFileDto = new BoardFileDto();
    boardFile.ifPresent(file -> BeanUtils.copyProperties(file, boardFileDto));
    return boardFileDto;
  }

  // insert, update
  @Transactional
  public void saveBoardFile(BoardFileDto boardFileDto) {
    boardFileRepository.save(boardFileDto.toEntity());
  }
}