package com.example.db.board.dto;

import com.example.db.board.domain.Board;
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
  private Long boardCommentIdx;
  private Long parentIdx;
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

  public Board toEntity() {
    return Board.builder()
        .boardIdx(boardIdx)
        .boardGroupIdx(boardGroupIdx)
        .boardCommentIdx(boardCommentIdx)
        .parentIdx(parentIdx)
        .title(title)
        .content(content)
        .isTop(isTop)
        .topDate(topDate)
        .isOpen(isOpen)
        .hit(hit)
        .category1(category1)
        .category2(category2)
        .isReply(isReply)
        .voc(voc)
        .sort(sort)
        .isHot(isHot)
        .isNew(isNew)
        .startDate(startDate)
        .endDate(endDate)
        .isAlltime(isAlltime)
        .link1(link1)
        .link2(link2)
        .link1Target(link1Target)
        .link2Target(link2Target)
        .registerDate(registerDate)
        .registerIdx(registerIdx)
        .modifyDate(modifyDate)
        .modifyIdx(modifyIdx)
        .isDelete(isDelete)
        .build();
  }
}
