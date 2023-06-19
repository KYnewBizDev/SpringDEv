package com.example.db.boardComment.domain;

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
public class BoardComment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  @Comment("게시판 댓글 PK")
  private Long boardCommentIdx;

  @Comment("게시판 그룹 PK")
  private Long boardGroupIdx;
  @Comment("게시판 PK")
  private Long boardIdx;
  @Comment("댓글내용")
  private String content;
  @Comment("하트개수")
  @ColumnDefault("0")
  private Integer heartNum;
  @Comment("답글채택여부")
  @Column(length = 1)
  @ColumnDefault("'N'")
  private String isChoose;

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
