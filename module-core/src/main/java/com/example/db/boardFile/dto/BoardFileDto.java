package com.example.db.boardFile.dto;

import com.example.db.boardFile.domain.BoardFile;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardFileDto {
  private Long boardFileIdx;
  private Long boardGroupIdx;
  private Long boardIdx;
  private String fileName;
  private String originalFileName;
  private String isMobile;
  private Integer sort;
  private LocalDateTime registerDate;
  private Long registerIdx;
  private LocalDateTime modifyDate;
  private Long modifyIdx;
  private String isDelete;

  public BoardFile toEntity() {
    return BoardFile.builder()
        .boardFileIdx(boardFileIdx)
        .boardGroupIdx(boardGroupIdx)
        .boardIdx(boardIdx)
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
