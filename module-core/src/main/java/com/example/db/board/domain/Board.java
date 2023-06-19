package com.example.db.board.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  @Comment("게시판 PK")
  private Long boardIdx;

  @Comment("게시판 그룹 PK")
  private Long boardGroupIdx;
  @Comment("부모 PK")
  private Long parentIdx;
  @Comment("채택 게시판 댓글 PK")
  private Long boardCommentIdx;
  @Comment("제목")
  private String title;
  @Comment("내용")
  @Column(columnDefinition="LONGTEXT")
  private String content;
  @Comment("상단 고정 여부")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isTop;
  @Comment("상단 고정 종료일")
  private LocalDateTime topDate;
  @Comment("노출 여부")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isOpen;
  @Comment("조회수")
  @ColumnDefault("0")
  private Integer hit;
  @Comment("카테고리 1 / 질문 영역/ 구분")
  private String category1;
  @Comment("카테고리 2 / 분류")
  private String category2;
  @Comment("답변 상태 / 처리 상태")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isReply;
  @Comment("voc 강도")
  private String voc;
  @Comment("정렬")
  @ColumnDefault("0")
  private Integer sort;
  @Comment("hot")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isHot;
  @Comment("new")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isNew;
  @Comment("이벤트 기간 시작일")
  private LocalDateTime startDate;
  @Comment("이벤트 기간 종료일")
  private LocalDateTime endDate;
  @Comment("이벤트 기간 상시 여부")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isAlltime;
  @Comment("영상 url / 링크(PC)")
  private String link1;
  @Comment("링크 주소(모바일)")
  private String link2;
  @Comment("링크타겟(PC)")
  @Column(name = "link1_target")
  private String link1Target;
  @Comment("링크타겟(모바일)")
  @Column(name = "link2_target")
  private String link2Target;

  @Comment("등록일")
  @Column(updatable = false)
  @ColumnDefault("CURRENT_TIMESTAMP")
  @CreatedDate
  private LocalDateTime registerDate;
  @Comment("작성자 PK")
  private Long registerIdx;
  @Comment("수정일")
  @Column(columnDefinition="datetime(6) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  @LastModifiedDate
  private LocalDateTime modifyDate;
  @Comment("수정자 PK")
  private Long modifyIdx;
  @Comment("삭제여부")
  @Column(length = 1, insertable = false)
  @ColumnDefault("'N'")
  private String isDelete;
}
