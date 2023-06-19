package com.example.db.boardGroup.domain;

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
public class BoardGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  @Comment("게시판 그룹 PK")
  private Long boardGroupIdx;

  @Comment("게시판명")
  private String boardName;
  @Comment("게시판 코드(테이블명)")
  private String boardCode;
  @Comment("설명")
  private String memo;
  @Comment("스킨")
  private String skin;
  @Comment("답변 사용 여부")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isReply;
  @Comment("댓글 사용 여부")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isComment;
  @Comment("댓글 좋아요 사용 여부")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isCommentHeart;
  @Comment("상단 고정 사용 여부")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isTop;
  @Comment("1:1 게시판 여부")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isPersonal;
  @Comment("공유하기 사용 여부")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isShare;
  @Comment("첨부파일 사용 여부")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isFile;
  @Comment("첨부파일 개수")
  @ColumnDefault("0")
  private Integer fileNum;
  @Comment("조회수 사용 여부")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isHit;
  @Comment("정렬순")
  private String orderby;
  @Comment("페이지당 게시글 수")
  @ColumnDefault("0")
  private Integer pageNum;
  @Comment("사용 여부")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isUse;

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
