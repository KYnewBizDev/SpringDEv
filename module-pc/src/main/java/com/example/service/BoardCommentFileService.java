package com.example.service;

import com.example.db.boardCommentFile.domain.BoardCommentFile;
import com.example.db.boardCommentFile.dto.BoardCommentFileDto;
import com.example.db.boardCommentFile.repository.BoardCommentFileQueryRepository;
import com.example.db.boardCommentFile.repository.BoardCommentFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardCommentFileService {
  private final BoardCommentFileRepository boardCommentFileRepository;
  private final BoardCommentFileQueryRepository boardCommentFileQueryRepository;

  // 리스트
  @Transactional(readOnly = true)
  public List<BoardCommentFileDto> getBoardCommentFileList(Long boardGroupIdx, Long boardCommentIdx) {
    List<BoardCommentFile> boardCommentFileList = boardCommentFileQueryRepository.findAll(boardGroupIdx, boardCommentIdx, "N");

    List<BoardCommentFileDto> boardCommentFileDtoList = new ArrayList<>();
    for (BoardCommentFile boardCommentFile : boardCommentFileList) {
      BoardCommentFileDto boardCommentFileDto = new BoardCommentFileDto();
      BeanUtils.copyProperties(boardCommentFile,boardCommentFileDto);
      boardCommentFileDtoList.add(boardCommentFileDto);
    }
    return boardCommentFileDtoList;
  }

  // 뷰
  @Transactional(readOnly = true)
  public BoardCommentFileDto getBoardCommentFile(Long boardGroupIdx, Long boardCommentIdx, Integer sort) {
    Optional<BoardCommentFile> boardCommentFile = boardCommentFileRepository.findByBoardGroupIdxAndBoardCommentIdxAndSortAndIsDelete(boardGroupIdx, boardCommentIdx, sort, "N");

    BoardCommentFileDto boardCommentFileDto = new BoardCommentFileDto();
    boardCommentFile.ifPresent(file -> BeanUtils.copyProperties(file, boardCommentFileDto));
    return boardCommentFileDto;
  }

  // insert, update
  @Transactional
  public void saveBoardCommentFile(BoardCommentFileDto boardCommentFileDto) {
    boardCommentFileRepository.save(boardCommentFileDto.toEntity());
  }
}