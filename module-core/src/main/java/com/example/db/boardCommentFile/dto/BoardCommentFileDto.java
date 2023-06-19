package com.example.db.boardCommentFile.dto;

import com.example.db.boardCommentFile.domain.BoardCommentFile;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardCommentFileDto {
  private Long boardCommentFileIdx;
  private Long boardGroupIdx;
  private Long boardCommentIdx;
  private String fileName;
  private String originalFileName;
  private String isMobile;
  private Integer sort;
  private LocalDateTime registerDate;
  private Long registerIdx;
  private LocalDateTime modifyDate;
  private Long modifyIdx;
  private String isDelete;

  public BoardCommentFile toEntity() {
    return BoardCommentFile.builder()
        .boardCommentFileIdx(boardCommentFileIdx)
        .boardGroupIdx(boardGroupIdx)
        .boardCommentIdx(boardCommentIdx)
        .fileName(fileName)
        .originalFileName(originalFileName)
        .isMobile(isMobile)
        .sort(sort)
        .registerDate(registerDate)
        .registerIdx(registerIdx)
        .modifyDate(modifyDate)
        .modifyIdx(modifyIdx)
        .isDelete(isDelete)
        .build();
  }
}
