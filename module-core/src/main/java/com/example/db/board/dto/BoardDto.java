package com.example.db.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardDto {
  private Long boardIdx;
  private Long boardGroupIdx;
  private Long parentIdx;
  private Long boardCommentIdx;
  private String title;
  private String content;
  private String isTop;
  private LocalDateTime topDate;
  private String isOpen;
  private Integer hit;
  private String category1;
  private String category2;
  private String isReply;
  private String voc;
  private Integer sort;
  private String isHot;
  private String isNew;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String isAlltime;
  private String link1;
  private String link2;
  private String link1Target;
  private String link2Target;
  private LocalDateTime registerDate;
  private Long registerIdx;
  private LocalDateTime modifyDate;
  private Long modifyIdx;
  private String isDelete;


  private String registerName; // 작성자 이름
  private String modifyName; // 수정자 이름
}
