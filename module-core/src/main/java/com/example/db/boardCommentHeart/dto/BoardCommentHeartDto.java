package com.example.db.boardCommentHeart.dto;

import com.example.db.boardCommentHeart.domain.BoardCommentHeart;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardCommentHeartDto {
  private Long boardCommentHeartIdx;
  private Long boardGroupIdx;
  private Long boardCommentIdx;
  private Long memberIdx;
  private LocalDateTime registerDate;
  private Long registerIdx;
  private LocalDateTime modifyDate;
  private Long modifyIdx;
  private String isDelete;

  public BoardCommentHeart toEntity() {
    return BoardCommentHeart.builder()
        .boardGroupIdx(boardGroupIdx)
        .boardGroupIdx(boardGroupIdx)
        .boardCommentIdx(boardCommentIdx)
        .memberIdx(memberIdx)
        .registerDate(registerDate)
        .registerIdx(registerIdx)
        .modifyDate(modifyDate)
        .modifyIdx(modifyIdx)
        .isDelete(isDelete)
        .build();
  }
}
