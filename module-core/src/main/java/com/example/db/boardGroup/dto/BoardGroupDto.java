package com.example.db.boardGroup.dto;

import com.example.db.boardGroup.domain.BoardGroup;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardGroupDto {
  private Long boardGroupIdx;
  private String boardName;
  private String boardCode;
  private String memo;
  private String skin;
  private String isReply;
  private String isComment;
  private String isCommentHeart;
  private String isTop;
  private String isPersonal;
  private String isShare;
  private String isFile;
  private Integer fileNum;
  private String isHit;
  private String orderby;
  private Integer pageNum;
  private String isUse;
  private LocalDateTime registerDate;
  private Long registerIdx;
  private LocalDateTime modifyDate;
  private Long modifyIdx;
  private String isDelete;

  public BoardGroup toEntity() {
    return BoardGroup.builder()
        .boardGroupIdx(boardGroupIdx)
        .boardName(boardName)
        .boardCode(boardCode)
        .memo(memo)
        .skin(skin)
        .isReply(isReply)
        .isComment(isComment)
        .isCommentHeart(isCommentHeart)
        .isTop(isTop)
        .isPersonal(isPersonal)
        .isShare(isShare)
        .isFile(isFile)
        .fileNum(fileNum)
        .isHit(isHit)
        .orderby(orderby)
        .pageNum(pageNum)
        .isUse(isUse)
        .registerDate(registerDate)
        .registerIdx(registerIdx)
        .modifyDate(modifyDate)
        .modifyIdx(modifyIdx)
        .isDelete(isDelete)
        .build();
  }
}
