package com.example.db.boardComment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardCommentDto {
  private Long boardCommentIdx;
  private Long boardGroupIdx;
  private Long boardIdx;
  private String content;
  private Integer heartNum;
  private String isChoose;
  private LocalDateTime registerDate;
  private Long registerIdx;
  private LocalDateTime modifyDate;
  private Long modifyIdx;
  private String isDelete;

  private String registerName; // 작성자 이름
  private String modifyName; // 수정자 이름
}
